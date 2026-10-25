package com.github.wohaopa.GTNHModify.util;

public final class ChanceBoost {

    /** 机器电压每高出一个等级，概率乘以该系数。 */
    private static final double PER_TIER = 1.05;
    /** 万分比上限，10000 = 100%。 */
    private static final int MAX_CHANCE = 10000;

    private ChanceBoost() {}

    /**
     * @param chance      配方原本的概率（万分比）
     * @param machineTier 机器当前的电压等级
     * @param recipeTier  配方所需的电压等级
     * @return 加成后、封顶 10000 的概率
     */
    public static int apply(int chance, int machineTier, int recipeTier) {
        if (chance <= 0 || chance >= MAX_CHANCE) return chance;
        int diff = machineTier - recipeTier;
        if (diff <= 0) return chance;
        return (int) Math.min(MAX_CHANCE, Math.round(chance * Math.pow(PER_TIER, diff)));
    }
}
