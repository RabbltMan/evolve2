package com.rabbltman.evolve2.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ZombieEntity.class)
public abstract class ZombieMixin extends LivingEntity {

    @Shadow public abstract int getXpToDrop();

    protected ZombieMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * @author RabbltMan
     * @reason Zombie
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createZombieAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 52.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0)
                .add(EntityAttributes.GENERIC_ARMOR, 8.0)
                .add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 4.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.2)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0);
    }


    @Inject(at = @At("HEAD"), method = "onKilledOther")
    public void mixinKillBuff(ServerWorld world, LivingEntity other, CallbackInfoReturnable<Boolean> cir) {
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 3));
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 200, 3));
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 2));
        this.setHealth(getHealth() + 3.0F);
    }

    @Inject(at = @At("HEAD"), method = "initEquipment")
    public void mixinInitEquipment(CallbackInfo ci) {
        if (random.nextFloat() <  0.5F) {
            int extraHP = random.nextInt(4);
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 999999, extraHP,false, false));
        }
        if (random.nextFloat() < (this.world.getDifficulty() == Difficulty.HARD ? 0.4F : 0.25F)) {
            int i = random.nextInt(10);
            int j = random.nextInt(10);
            int amp = random.nextInt(4);
            if (i == 0) {
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 999999, amp, false, false));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 999999, amp,false, false));
            } else if (i == 1) {
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 999999, amp,false, false));
            } else if (i == 2) {
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 999999, amp,false, false));
            } else if (i == 3){
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 999999, amp,false, false));
            } else if (i == 4) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 999999, amp,false, false));
            }
            if (j == 0) {
                this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
                this.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
                this.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
                this.equipStack(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
            } else if (j == 1) {
                this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
                this.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
                this.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS));
                this.equipStack(EquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS));
            } else if (j == 2){
                this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
            }
        }

    }
}



