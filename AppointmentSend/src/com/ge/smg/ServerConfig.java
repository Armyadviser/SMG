package com.ge.smg;

import com.ge.util.IniOperation;

import java.io.File;

/**
 * Created by falcon on 16-12-27.
 *
 */
public class ServerConfig {

    private final IniOperation mIni;

    private static ServerConfig instance;

    private ServerConfig() {
        mIni = new IniOperation();
        String iniPath = System.getProperty("user.dir") + File.separatorChar + "server.ini";
        if (!mIni.loadIni(iniPath)) {
            System.out.println("Load ini file error." + iniPath);
            System.exit(-1);
        }
    }

    public static ServerConfig getInstance() {
        if (instance == null) {
            instance = new ServerConfig();
        }
        return instance;
    }

    public String getValue(String section, String key) {
        return mIni.getKeyValue(section, key);
    }
}
