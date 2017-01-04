package com.ge.smg.vo;

import java.time.LocalTime;

/**
 * Created by Storm_Falcon on 2016/12/21.
 *
 */
@SuppressWarnings("unused")
public class ProcessInfo {
	public String user;
	public int pid;
	public int parentPid;
	public int priority;
	public String startTime;
	public String terminal;
	public LocalTime time;
	public String cmd;

	@Override
	public String toString() {
		if (cmd != null) {
		    int index = cmd.lastIndexOf(" ");
		    if (index != -1) {
                cmd = cmd.substring(index + 1, cmd.length());
            }
        }

		return "ProcessInfo{" +
				"user='" + user + '\'' +
				", pid=" + pid +
				", cmd='" + cmd + '\'' +
				'}';
	}
}
