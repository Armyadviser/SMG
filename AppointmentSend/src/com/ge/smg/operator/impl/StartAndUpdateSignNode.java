package com.ge.smg.operator.impl;

import com.ge.smg.operator.HandleNode;
import com.ge.smg.vo.AppointmentItem;
import com.ge.smg.vo.AppointmentItemBean;
import com.ge.smg.vo.ProcessBean;
import com.ge.smg.vo.ProcessInfo;

/**
 * Created by Storm_Falcon on 2016/12/27.
 * Start process and Update sign.
 * When AppointmentItem is not done(sign != 2)
 * and ProcessInfo not exists.
 */
public class StartAndUpdateSignNode implements HandleNode {
	@Override
	public boolean accept(AppointmentItem appointmentItem, ProcessInfo processInfo) {
		if (processInfo != null || appointmentItem.sign == 2) {
			return false;
		}

		AppointmentItemBean.startSend(appointmentItem);
		ProcessBean.startSend(appointmentItem.actionCode);

		return true;
	}
}
