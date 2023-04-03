package com.rabbltman.evolve2.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;



@Mixin(AbstractSkeletonEntity.class)
public abstract class SkeletonAttackMixin extends LivingEntity {


    protected SkeletonAttackMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(at = @At("HEAD"), method = "createArrowProjectile", ordinal = 0, argsOnly = true)
    private float mixinArrow(float damageModifier) {
        return damageModifier * (0.65F + 1.1F * random.nextFloat());
    }

}
