package com.github.wohaopa.GTNHModify.tweakers.gt;

import gregtech.api.util.GTRecipe;

/**
 * Chance：产物概率的保底值（万分比，10000 = 100%），只对原本概率大于 0 的产物生效。
 * ChanceRate：保底结算之后再乘以该倍率，结果最高 10000（100%）。
 */
public class OutputChanceTweaker extends GT_RecipeTweaker {

    private double chance = 0;
    private double rate = 1;

    @Override
    protected void modifyGT_Recipe(GTRecipe aRecipe) {
        if (aRecipe.mOutputs == null) return;
        int[] chances = new int[aRecipe.mOutputs.length];
        for (int i = 0; i < chances.length; i++) {
            int old = aRecipe.getOutputChance(i);
            // 只拉高保底，不把概率为 0 的产物变成有概率
            int raised = old > 0 ? Math.max(old, (int) chance) : old;
            // 再乘倍率，限制在 0 ~ 10000
            chances[i] = (int) Math.min(Math.max(raised * rate, 0), 10000);
        }
        aRecipe.mChances = chances;
    }

    @Override
    protected void modifyGT_Recipe_AssemblyLine(GTRecipe.RecipeAssemblyLine aRecipe) {
        // 装配线配方没有概率字段，产物固定 100%
    }

    @Override
    public String[] getSettingKeys() {
        return new String[] { "Chance", "ChanceRate" };
    }

    @Override
    public Object getSettings(String key) {
        if ("Chance".equals(key)) return chance;
        if ("ChanceRate".equals(key)) return rate;
        return null;
    }

    @Override
    public void setSetting(String key, Object setting) {
        if (setting == null) return;
        double value = ((Number) setting).doubleValue();
        if ("Chance".equals(key)) {
            chance = Math.min(Math.max(value, 0), 10000);
        } else if ("ChanceRate".equals(key)) {
            rate = Math.max(value, 0);
        }
    }
}
