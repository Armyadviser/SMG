package com.ge.smg;

import com.ge.smg.operator.HandleNodeManager;
import com.ge.smg.operator.impl.*;
import com.ge.smg.vo.*;
import com.ge.util.DateTimeHelper;
import com.ge.util.db.DBUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Storm_Falcon on 2016/12/21.
 *
 */
public class SmgServer extends Thread {

	private HandleNodeManager chainManager;

	private SmgServer() {
		initDutyChain();
	}

	private void initDutyChain() {
		chainManager = HandleNodeManager.getInstance();
		chainManager.addHandleNode(new RejectNode());
		chainManager.addHandleNode(new StartAndUpdateSignNode());
        chainManager.addHandleNode(new KillNode());
        chainManager.addHandleNode(new RestartAndUpdateSignNode());
        chainManager.addHandleNode(new FinishNode());
		chainManager.addHandleNode(new LastNode());
	}

	private AppointmentItem getAppointmentItem() {
		//not over
		String sql = "select * from smg_appointment_send where sign = 1";
		List<AppointmentItem> appointmentList = DBUtil.select(sql, AppointmentItemBean.resultSetMapper);
		if (!appointmentList.isEmpty()) {
			return appointmentList.get(0);
		}

		//not start send
		sql = "select * from smg_appointment_send " +
			"where sign = 0 " +
			"and send_t <= '" + DateTimeHelper.toSimpleString(LocalDateTime.now()) + "' " +
			"order by send_t";
        appointmentList = DBUtil.select(sql, AppointmentItemBean.resultSetMapper);
		if (!appointmentList.isEmpty()) {
			return appointmentList.get(0);
		}
		return null;
	}

	public void run() {
        ServerConfig config = ServerConfig.getInstance();
        int sleep = Integer.parseInt(config.getValue("Server", "Sleep")) * 60 * 1000;

		initDutyChain();

		while (true) {
			try {
				AppointmentItem appointmentItem = getAppointmentItem();
				ProcessInfo process = ProcessBean.getProcess();

				chainManager.handleRequest(appointmentItem, process);

				ProcessWrapper processWrapper = ProcessWrapper.getInstance();
				processWrapper.setProcess(process);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				sleep(sleep);
			}
		}
	}

	private void sleep(int sleep) {
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		new SmgServer().start();
	}
}
