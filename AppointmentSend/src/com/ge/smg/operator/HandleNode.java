package com.ge.smg.operator;

import com.ge.smg.vo.AppointmentItem;
import com.ge.smg.vo.ProcessInfo;

/**
 * Created by Storm_Falcon on 2016/12/27.
 *
 */
public interface HandleNode {

	/**
	 * Process request.
	 * @param appointmentItem appointmentItem
	 * @param processInfo processInfo
	 * @return accepted return true, else return false.
	 */
	boolean accept(AppointmentItem appointmentItem, ProcessInfo processInfo);
}
