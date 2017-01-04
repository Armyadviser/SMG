package com.ge.util.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Storm_Falcon on 2016/12/21.
 * Declare how to map {@code java.sql.ResultSet} to an Object.
 */
public interface ResultSetMapper<R> {
	R map(ResultSet resultSet);

	default List<Map<String, Object>> parseResultSet(ResultSet resultSet) {
		try {
			List<Map<String, Object>> list = new ArrayList<>();

			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();

			while (resultSet.next()) {
				Map<String, Object> map = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String label = metaData.getColumnLabel(i);
					Object obj = resultSet.getObject(i);
					map.put(label, obj);
				}
				list.add(map);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
