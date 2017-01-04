package com.ge.smg.operator.impl;

import com.ge.smg.operator.HandleNode;
import com.ge.smg.vo.AppointmentItem;
import com.ge.smg.vo.AppointmentItemBean;
import com.ge.smg.vo.ProcessInfo;
import com.ge.smg.vo.ProcessWrapper;

/**
 * Created by Storm_Falcon on 2016/12/29.
 *
 */
public class FinishNode implements HandleNode {
	@Override
	public boolean accept(AppointmentItem appointmentItem, ProcessInfo processInfo) {
		if (appointmentItem == null) {
			return false;
		}

		ProcessWrapper processWrapper = ProcessWrapper.getInstance();
		if (processWrapper.isPresent() && processInfo == null) {
			AppointmentItemBean.finishSend(appointmentItem);
			return true;
		}
		return false;
	}
}
