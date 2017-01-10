package com.ge.smg.operator.impl;

import com.ge.smg.operator.HandleNode;
import com.ge.smg.vo.AppointmentItem;
import com.ge.smg.vo.ProcessBean;
import com.ge.smg.vo.ProcessInfo;

import java.time.LocalDateTime;

/**
 * Created by Storm_Falcon on 2016/12/27.
 * When passed appointment end time, kill process.
 */
public class KillNode implements HandleNode {
	@Override
	public boolean accept(AppointmentItem appointmentItem, ProcessInfo processInfo) {
		if (appointmentItem == null || processInfo == null) {
			return false;
		}

		if (appointmentItem.endTime == null) {
            return false;
        }

		if (appointmentItem.endTime.isAfter(LocalDateTime.now())) {
			return false;
		}

		ProcessBean.killProcess(processInfo);
		return true;
	}
}
