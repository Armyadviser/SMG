package com.ge.util.db;

import com.ge.util.IniOperation;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Storm_Falcon on 2016/12/21.
 * mysql connector.
 */
@SuppressWarnings("CanBeFinal")
public class DBUtil {

	private static String URL;
	private static String USR;
	private static String PWD;

	static {
		String iniPath = System.getProperty("user.dir") + File.separatorChar + "server.ini";
		IniOperation ini = new IniOperation();
		if (!ini.loadIni(iniPath)) {
			System.out.println("Load ini error." + iniPath);
			System.exit(-1);
		}

		try {
			Class.forName(ini.getKeyValue("DB", "Driver"));
			URL = ini.getKeyValue("DB", "Url");
			USR = ini.getKeyValue("DB", "Username");
			PWD = ini.getKeyValue("DB", "Password");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static Connection newConn() {
		try {
			return DriverManager.getConnection(URL, USR, PWD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Set objects to prepareStatement.
	 * @param sql SQL with ?
	 * @param args param list
	 */
	private static PreparedStatement prepare(Connection conn, String sql, Object... args) {
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			IntStream.rangeClosed(1, args.length)
				.peek(System.out::println)
				.forEach(i -> setParameterToPreparedStatement(stmt, i, args[i]));
			return stmt;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void setParameterToPreparedStatement(PreparedStatement stmt, int index, Object obj) {
		try {
			stmt.setObject(index, obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void freeResource(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int update(String sql, Object... args) {
        Connection conn = newConn();
		PreparedStatement stmt = prepare(conn, sql, args);
		if (stmt == null) {
			return 0;
		}
		try {
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			freeResource(conn, stmt, null);
		}
		return 0;
	}

	/**
	 * Search
	 * @param mapper Declare how to map each {@code java.sql.ResultSet} to an object.
	 * @param sql SQL.
	 * @param args Parameter list.
	 * @param <R> Type of map result.
	 * @return A {@code java.util.List} consists of R.
	 */
	public static <R> List<R> select(String sql, ResultSetMapper<R> mapper, Object... args) {
		Connection conn = newConn();
		PreparedStatement stmt = prepare(conn, sql, args);
		ResultSet rs = null;
		try {
			if (stmt == null) {
				return Collections.emptyList();
			}
			rs = stmt.executeQuery();

			List<R> list = new ArrayList<>();
			while (rs.next()) {
				list.add(mapper.map(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			freeResource(conn, stmt, rs);
		}
		return Collections.emptyList();
	}

	/**
	 * Save an object to db.
	 * @param e vo.
	 * @param mapper Declare how to convert a vo to insert sql.
	 * @param <E> Type of vo.
	 * @return Success return 1, else return 0.
	 */
	public static <E> int insert(E e, ObjectMapper<E> mapper) {
		Connection conn = newConn();
		if (conn == null) {
			return 0;
		}
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String sql = mapper.map(e);
			return stmt.executeUpdate(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			freeResource(conn, stmt, null);
		}
		return 0;
	}
}
