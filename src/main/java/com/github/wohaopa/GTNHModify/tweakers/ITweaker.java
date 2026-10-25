package com.github.wohaopa.GTNHModify.tweakers;

public abstract class ITweaker {

    private boolean done = false;

    public void apply0() {
        if (done) return;
        done = true;
        apply();
    }

    protected abstract void apply();

    void exportRecipe() {}

    public Object getSettings() {
        return null;
    }

    public void setSetting(Object setting) {}

    /**
     * 需要多个配置参数的执行器返回参数名数组，Config 会为每个名字生成一项配置。
     * 返回 null 时走单参数逻辑（name_setting）。
     */
    public String[] getSettingKeys() {
        return null;
    }

    public Object getSettings(String key) {
        return null;
    }

    public void setSetting(String key, Object setting) {}
}
