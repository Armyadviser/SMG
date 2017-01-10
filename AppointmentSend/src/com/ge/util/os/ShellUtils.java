package com.ge.util.os;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Storm_Falcon on 2016/12/21.
 * Execute bash command.
 */
public class ShellUtils {
	public static <E> List<E> execute(String cmd, File dir, ResultParser<E> parser) {
		Process process = null;
		try {
			String[] cmds = {"/bin/bash", "-c", cmd};
			Runtime runtime = Runtime.getRuntime();
			process = runtime.exec(cmds, null, dir);
			process.waitFor();

			if (parser == null) {
				return Collections.emptyList();
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			List<E> list = new ArrayList<>();

			String line;
			while ((line = reader.readLine()) != null) {
				list.add(parser.parse(line));
			}

			reader.close();

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (process != null) {
				process.destroy();
			}
		}
		return Collections.emptyList();
	}

	public static void execute(String cmd) {
		execute(cmd, null, null);
	}
}
