package com.rabbltman.evolve2.mixin;

import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EnderDragonEntity.class)
public abstract class DragonMixin {
    @ModifyConstant(method = "createEnderDragonAttributes", constant = @Constant(doubleValue = 200.0))
    private static double health(double x) {
        return 480.0;
    }
}
