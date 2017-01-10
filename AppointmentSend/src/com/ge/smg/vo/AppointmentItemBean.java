package com.ge.smg.vo;

import com.ge.util.DateTimeHelper;
import com.ge.util.db.DBUtil;
import com.ge.util.db.ObjectMapper;
import com.ge.util.db.ResultSetMapper;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Created by Storm_Falcon on 2016/12/21.
 *
 */
public class AppointmentItemBean {

	public static final ResultSetMapper<AppointmentItem> resultSetMapper = resultSet -> {
			try {
				String sId = resultSet.getString("Id");
				String sSendTime = resultSet.getString("send_t");
				String sCreateTime = resultSet.getString("create_t");
				String sStartTime = resultSet.getString("start_t");
                String sEndTime = resultSet.getString("end_t");
				String actionCode = resultSet.getString("action_code");
				String sSign = resultSet.getString("sign");

				AppointmentItem item = new AppointmentItem();
				item.id = Long.parseLong(sId);
				item.createTime = DateTimeHelper.parse(sCreateTime);
				item.sendTime = DateTimeHelper.parse(sSendTime);
				item.startTime = DateTimeHelper.parse(sStartTime);
				item.endTime = DateTimeHelper.parse(sEndTime);
				item.actionCode = actionCode;
				item.sign = Integer.parseInt(sSign);

				return item;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		};

	public static boolean save(AppointmentItem appointmentItem) {
		return DBUtil.insert(appointmentItem, objectMapper) == 1;
	}

	public static final ObjectMapper<AppointmentItem> objectMapper = appointmentItem ->
				"insert into smg_appointment_send (create_t, send_t, start_t, end_t, action_code, sign) " +
			"values ('" + DateTimeHelper.toSimpleString(appointmentItem.createTime) + "', " +
			"'" + DateTimeHelper.toSimpleString(appointmentItem.sendTime) + "', " +
			"'" + DateTimeHelper.toSimpleString(appointmentItem.startTime) + "', " +
			"'" + DateTimeHelper.toSimpleString(appointmentItem.endTime) + "', " +
			"'" + appointmentItem.actionCode + "', " +
			appointmentItem.sign + ")";

	public static void startSend(AppointmentItem item) {
		String sql = "update smg_appointment_send " +
			"set sign=1, " +
			"start_t='" + DateTimeHelper.toSimpleString(LocalDateTime.now()) + "' " +
			"where Id='" + item.id + "'";
		DBUtil.update(sql);
	}

	public static void finishSend(AppointmentItem item) {
		String sql = "update smg_appointment_send " +
			"set sign=2, " +
			"finish_t='" + DateTimeHelper.toSimpleString(LocalDateTime.now()) + "' " +
			"where Id='" + item.id + "'";
		DBUtil.update(sql);
	}
}
