package com.github.wohaopa.GTNHModify.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.github.wohaopa.GTNHModify.GTNHModifyMod;
import com.github.wohaopa.GTNHModify.tweakers.ITweaker;
import com.github.wohaopa.GTNHModify.tweakers.Tweakers;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class Config {

    public static Configuration config;

    private static boolean doSave;

    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
        }
        doSave = false;
        loadConfig();
        FMLCommonHandler.instance()
            .bus()
            .register(new Config());
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(GTNHModifyMod.MODID)) {
            loadConfig();
        }
    }

    private static void loadConfig() {

        for (Tweakers tweaker : Tweakers.values()) {
            tweaker.enabled = config
                .getBoolean(tweaker.name, Configuration.CATEGORY_GENERAL, false, tweaker.description);

            ITweaker instance = tweaker.tweaker;
            String[] keys = instance.getSettingKeys();
            if (keys == null) {
                // 单参数：配置项名为 <开关名>_setting
                Object value = readValue(tweaker.name + "_setting", instance.getSettings(), tweaker.description);
                if (value != null) instance.setSetting(value);
            } else {
                // 多参数：配置项名为 <枚举名>.<参数名>，避免与其它 mod 撞名
                for (String key : keys) {
                    Object value = readValue(
                        tweaker.name() + "." + key,
                        instance.getSettings(key),
                        tweaker.description);
                    if (value != null) instance.setSetting(key, value);
                }
            }
        }

        if (config.hasChanged() || doSave) {
            config.save();
            doSave = false;
        }
    }

    /**
     * 按默认值的运行时类型决定用哪种方式读取配置，返回 null 表示不支持该类型。
     */
    private static Object readValue(String key, Object current, String comment) {
        if (current == null) return null;
        if (current instanceof String) {
            return config.getString(key, Configuration.CATEGORY_GENERAL, (String) current, comment);
        }
        if (current instanceof Boolean) {
            return config.getBoolean(key, Configuration.CATEGORY_GENERAL, (Boolean) current, comment);
        }
        if (current instanceof Integer) {
            return config.getInt(
                key,
                Configuration.CATEGORY_GENERAL,
                (Integer) current,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE,
                comment);
        }
        if (current instanceof Double) {
            // Forge 1.7.10 没有 getDouble，用 getFloat 读再转回 double
            return (double) config.getFloat(
                key,
                Configuration.CATEGORY_GENERAL,
                ((Double) current).floatValue(),
                -Float.MAX_VALUE,
                Float.MAX_VALUE,
                comment);
        }
        if (current instanceof Float) {
            return config.getFloat(
                key,
                Configuration.CATEGORY_GENERAL,
                (Float) current,
                -Float.MAX_VALUE,
                Float.MAX_VALUE,
                comment);
        }
        if (current instanceof String[]) {
            return config.getStringList(key, Configuration.CATEGORY_GENERAL, (String[]) current, comment);
        }
        return null;
    }

}
