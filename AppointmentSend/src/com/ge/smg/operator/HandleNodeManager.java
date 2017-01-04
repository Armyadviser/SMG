package com.ge.smg.operator;

import com.ge.smg.vo.AppointmentItem;
import com.ge.smg.vo.ProcessInfo;
import com.ge.util.DateTimeHelper;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Storm_Falcon on 2016/12/27.
 *
 */
public class HandleNodeManager {

	private final List<HandleNode> chain;

	private HandleNodeManager() {
		chain = new LinkedList<>();
	}

	private static HandleNodeManager instance;
	public static synchronized HandleNodeManager getInstance() {
		if (instance == null) {
			instance = new HandleNodeManager();
		}
		return instance;
	}

	public void addHandleNode(HandleNode node) {
		chain.add(node);
	}

	public void handleRequest(AppointmentItem appointmentItem, ProcessInfo processInfo) {
        System.out.println(appointmentItem + "\t" + processInfo);
		for (HandleNode node : chain) {
			String nodeName = node.getClass().getSimpleName();
			try {
				boolean done = node.accept(appointmentItem, processInfo);
				if (done) {
					System.out.printf("[%s] %s %s\n",
                            DateTimeHelper.toSimpleString(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss"),
							"request accepted by", nodeName);
					break;
				}
			} catch (NullPointerException e) {
				System.out.println("NullPointerException occur at " + nodeName);
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        System.out.println("-----------------------------------------------\n");
    }
}
