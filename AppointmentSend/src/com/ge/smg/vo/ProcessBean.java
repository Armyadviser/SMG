package com.ge.smg.vo;

import com.ge.smg.ServerConfig;
import com.ge.util.StringHelper;
import com.ge.util.os.ResultParser;
import com.ge.util.os.ShellUtils;

import java.io.File;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by Storm_Falcon on 2016/12/27.
 *
 */
public class ProcessBean {

	public static final ResultParser<ProcessInfo> resultParser = content -> {
        content = content.replaceAll("\\s+", " ");
        String[] items = content.split(" ");
        if (items.length < 7) {
            return null;
        }

        ProcessInfo info = new ProcessInfo();
        info.user = items[0];
        info.pid = Integer.parseInt(items[1]);
        info.parentPid = Integer.parseInt(items[2]);
        info.priority = Integer.parseInt(items[3]);
        info.startTime = items[4];
        info.terminal = items[5];
        info.time = LocalTime.parse(items[6]);
        info.cmd = StringHelper.union(items, 7, items.length, " ");
        return info;
    };

    public static ProcessInfo getProcess() {
        ServerConfig config = ServerConfig.getInstance();
        String cmd = config.getValue("Process", "SearchCmd");
        String dir = config.getValue("Process", "Dir");
        List<ProcessInfo> processList = ShellUtils.execute(cmd, new File(dir), ProcessBean.resultParser);
        if (processList.isEmpty()) {
            return null;
        }
        return processList.get(0);
    }

	public static void startSend(String actionCode) {
        ServerConfig config = ServerConfig.getInstance();
        String cmd = config.getValue("Process", "StartCmd");
        String dir = config.getValue("Process", "Dir");
		cmd = cmd + " " + actionCode;
        System.out.println(cmd);
        ShellUtils.execute(cmd, new File(dir), ProcessBean.resultParser);
	}

	public static void killProcess(ProcessInfo info) {
		String cmd = "kill -9 " + info.pid;
        System.out.println(cmd);
        ShellUtils.execute(cmd);
	}
}
