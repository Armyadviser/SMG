package com.ge.smg.vo;

import java.time.LocalDateTime;

/**
 * Created by Storm_Falcon on 2016/12/21.
 * smsehall.smg_appointment_send
 */
public class AppointmentItem {
	public long id;
	public LocalDateTime createTime;
	public LocalDateTime sendTime;
	public LocalDateTime startTime;
	public LocalDateTime endTime;
	public String actionCode;
	public int sign;

	@Override
	public String toString() {
		return "AppointmentItem{" +
				"sendTime=" + sendTime +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", actionCode='" + actionCode + '\'' +
				", sign=" + sign +
				'}';
	}
}
