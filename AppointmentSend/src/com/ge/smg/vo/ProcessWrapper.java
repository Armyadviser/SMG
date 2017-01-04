package com.ge.smg.vo;

/**
 * Created by Storm_Falcon on 2016/12/29.
 *
 */
public class ProcessWrapper {

	private ProcessInfo process;

	private ProcessWrapper() {
	}

	private static ProcessWrapper instance;

	public static synchronized ProcessWrapper getInstance() {
		if (instance == null) {
			instance = new ProcessWrapper();
		}
		return instance;
	}

	public boolean isPresent() {
		return process != null;
	}

	public void setProcess(ProcessInfo info) {
		this.process = info;
	}

	public ProcessInfo getProcess() {
		return process;
	}
}
