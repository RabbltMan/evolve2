package com.rabbltman.evolve2.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombifiedPiglinEntity.class)
public abstract class ZombiePiglinMixin extends LivingEntity {
    protected ZombiePiglinMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * @author RabbltMan
     * @reason ZombiePiglin
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createZombifiedPiglinAttributes() {
        return ZombieEntity.createZombieAttributes().add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS, 0.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }

    @Inject(at = @At("HEAD"), method = "initEquipment", cancellable = true)
    public void mixinInitEquipment(CallbackInfo ci) {
        ci.cancel();
        if (random.nextInt(10) <= 3) {
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_AXE));
        } else {
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
        }
        if (random.nextInt(10) <= 4) {
            this.equipStack(EquipmentSlot.MAINHAND, EnchantmentHelper.enchant(random, this.getMainHandStack(), 30, true));
        }
    }

    @Inject(at = @At("TAIL"), method = "setTarget")
    public void mixinGettingAngry(CallbackInfo ci) {
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 200, 7,false, false));
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 2,false, false));
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 2,false, false));
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 400, 0,false, false));
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 200, 0,false, true));
    }
}
