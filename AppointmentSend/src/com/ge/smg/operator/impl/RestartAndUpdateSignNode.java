package com.ge.smg.operator.impl;

import com.ge.smg.operator.HandleNode;
import com.ge.smg.vo.AppointmentItem;
import com.ge.smg.vo.AppointmentItemBean;
import com.ge.smg.vo.ProcessBean;
import com.ge.smg.vo.ProcessInfo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by Storm_Falcon on 2016/12/27.
 * Restart send then update sign and start time
 * when appointment end time has timeout.
 */
public class RestartAndUpdateSignNode implements HandleNode {
	@Override
	public boolean accept(AppointmentItem appointmentItem, ProcessInfo processInfo) {
		if (appointmentItem == null || processInfo == null) {
			return false;
		}

		if (appointmentItem.startTime == null) {
			return false;
		}

		//haven't timeout && during less than 2 hours.
		if (appointmentItem.endTime.isAfter(LocalDateTime.now())
                && appointmentItem.startTime.plus(2L, ChronoUnit.HOURS)
                    .isAfter(LocalDateTime.now())) {
			return false;
		}

		ProcessBean.killProcess(processInfo);
		ProcessBean.startSend(appointmentItem.actionCode);
		AppointmentItemBean.startSend(appointmentItem);

		return true;
	}
}
