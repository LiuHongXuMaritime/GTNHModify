package com.github.wohaopa.GTNHModify.mixins.late.gregtech;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.wohaopa.GTNHModify.tweakers.Tweakers;
import com.github.wohaopa.GTNHModify.util.ChanceBoost;

import gregtech.api.interfaces.tileentity.IVoidable;
import gregtech.api.metatileentity.implementations.MTEMultiBlockBase;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ParallelHelper;

/**
 * 多方块机器：概率计算集中在 ParallelHelper，用控制器的输入电压等级做加成。
 */
@Mixin(value = ParallelHelper.class, remap = false)
public class ParallelHelperChanceMixin {

    @Shadow
    private IVoidable machine;

    @Redirect(
        method = "calculateItemOutputs",
        at = @At(value = "INVOKE", target = "Lgregtech/api/util/GTRecipe;getOutputChance(I)I"))
    private int gtNHModify$boostOutputChance(GTRecipe recipe, int index) {
        if (!Tweakers.VoltageChance.enabled) return recipe.getOutputChance(index);
        int machineTier = 0;
        if (machine instanceof MTEMultiBlockBase) {
            machineTier = GTUtility.getTier(((MTEMultiBlockBase) machine).getMaxInputVoltage());
        }
        return ChanceBoost.apply(recipe.getOutputChance(index), machineTier, GTUtility.getTier(recipe.mEUt));
    }
}
