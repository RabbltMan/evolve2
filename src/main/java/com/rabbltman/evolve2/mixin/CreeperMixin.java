package com.rabbltman.evolve2.mixin;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Iterator;

@Mixin(CreeperEntity.class)
public abstract class CreeperMixin extends LivingEntity {
    @Shadow protected abstract void explode();

    protected CreeperMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "handleFallDamage")
    public void MixinFall(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        float chance = fallDistance * (4 + random.nextInt(6));
        if (chance >= 40) {
            this.explode();
        }
    }

    @Inject(at = @At("HEAD"), method = "explode", cancellable = true)
    private void MixinExplode(CallbackInfo ci) {
        ci.cancel();
        this.clearStatusEffects();
        if (random.nextInt(10) <= 5) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 250, 2));
        }
        if (random.nextInt(10) <= 5) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 2));
        }
        if (random.nextInt(10) < 7) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 2));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 100, 2));
        }
        if (random.nextInt(10) <= 5) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 2));
        }
        if (random.nextInt(10) <= 5) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 200, 2));
        }
        if (random.nextInt(10) <= 1) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 200, -2));
        }
        float explodePower = (1 + random.nextInt(3)) * 4;
        if (!this.world.isClient) {
            this.dead = true;
            boolean fire = random.nextInt(5) < 2;
            this.spawnEffectsCloud();
            this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), explodePower, fire, World.ExplosionSourceType.MOB);
            this.discard();
        }
    }

    private void spawnEffectsCloud() {
        Collection<StatusEffectInstance> collection = this.getStatusEffects();
        if (!collection.isEmpty()) {
            int duration = 15;
            AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
            areaEffectCloudEntity.setRadius(1.0F + random.nextInt(5) * 0.35F);
            areaEffectCloudEntity.setRadiusOnUse(areaEffectCloudEntity.getRadius());
            areaEffectCloudEntity.setWaitTime(10);
            areaEffectCloudEntity.setDuration(duration);
            areaEffectCloudEntity.setRadiusGrowth(areaEffectCloudEntity.getRadius() / 2.2F);
            Iterator var3 = collection.iterator();

            while(var3.hasNext()) {
                StatusEffectInstance statusEffectInstance = (StatusEffectInstance)var3.next();
                areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
            }

            this.world.spawnEntity(areaEffectCloudEntity);
        }
    }
}
