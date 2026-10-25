package com.github.wohaopa.GTNHModify.mixins.late.gregtech;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.wohaopa.GTNHModify.tweakers.Tweakers;
import com.github.wohaopa.GTNHModify.util.ChanceBoost;

import gregtech.api.metatileentity.implementations.MTEBasicMachine;
import gregtech.api.metatileentity.implementations.MTETieredMachineBlock;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;

/**
 * 单方块机器：产物概率按“机器电压等级 - 配方电压等级”加成。
 */
@Mixin(value = MTEBasicMachine.class, remap = false)
public abstract class MTEBasicMachineChanceMixin {

    @Redirect(
        method = "checkRecipe(Z)I",
        at = @At(value = "INVOKE", target = "Lgregtech/api/util/GTRecipe;getOutputChance(I)I"))
    private int gtNHModify$boostOutputChance(GTRecipe recipe, int index) {
        if (!Tweakers.VoltageChance.enabled) return recipe.getOutputChance(index);
        int machineTier = ((MTETieredMachineBlock) ((Object) this)).mTier;
        return ChanceBoost.apply(recipe.getOutputChance(index), machineTier, GTUtility.getTier(recipe.mEUt));
    }
}
