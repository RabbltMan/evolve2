package com.rabbltman.evolve2.mixin;

import net.minecraft.entity.boss.WitherEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(WitherEntity.class)
public abstract class WitherBossMixin {
    @ModifyConstant(method = "createWitherAttributes", constant = @Constant(doubleValue = 300.0))
    private static double health(double x) {
        return 550.0;
    }

    @ModifyConstant(method = "createWitherAttributes", constant = @Constant(doubleValue = 4.0))
    private static double defence(double x) {
        return 10.0;
    }
}
