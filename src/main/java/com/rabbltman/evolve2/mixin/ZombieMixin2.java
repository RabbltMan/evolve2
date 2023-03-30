package com.rabbltman.evolve2.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public class ZombieMixin2 extends MobEntity{

    protected ZombieMixin2(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "initGoals")
    public void mixinInitGoals(CallbackInfo ci) {
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, PigEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, CowEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, SheepEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, ChickenEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, RabbitEntity.class, true));
    }
}