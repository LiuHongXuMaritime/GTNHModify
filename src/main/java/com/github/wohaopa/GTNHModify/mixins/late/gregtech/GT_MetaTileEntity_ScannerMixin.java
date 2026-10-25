package com.github.wohaopa.GTNHModify.mixins.late.gregtech;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import com.github.wohaopa.GTNHModify.tweakers.handler.GregTechHandler;

import gregtech.common.tileentities.machines.basic.MTEScanner;

@Mixin(value = MTEScanner.class, remap = false)
public abstract class GT_MetaTileEntity_ScannerMixin {

    @ModifyArg(
        method = "checkRecipe()I",
        at = @At(
            value = "INVOKE",
            target = "Lgregtech/common/tileentities/machines/basic/MTEScanner;calculateOverclockedNess(II)V"),
        index = 1)
    private int injected(int x) {
        return GregTechHandler.instance.handle(this, x);
    }
}
