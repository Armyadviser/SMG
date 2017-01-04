package com.ge.smg.operator.impl;

import com.ge.smg.operator.HandleNode;
import com.ge.smg.vo.AppointmentItem;
import com.ge.smg.vo.ProcessInfo;

/**
 * Created by Storm_Falcon on 2016/12/27.
 * The last node when others didn't accept.
 */
public class LastNode implements HandleNode {
	@Override
	public boolean accept(AppointmentItem appointmentItem, ProcessInfo processInfo) {
		return true;
	}
}
