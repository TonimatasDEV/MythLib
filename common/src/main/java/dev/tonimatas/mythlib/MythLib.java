package dev.tonimatas.mythlib;

import com.mojang.logging.LogUtils;

public class MythLib {
    public static final String MOD_ID = "mythlib";
    public static final String DATA = "MythData";

    public static void init() {
        LogUtils.getLogger().info("MythLib has been loaded successfully.");
    }
}
