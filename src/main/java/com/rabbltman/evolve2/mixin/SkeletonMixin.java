package com.rabbltman.evolve2.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.ObjectInputStream;


@Mixin(AbstractSkeletonEntity.class)
public abstract class SkeletonMixin extends LivingEntity{


    @Shadow public abstract void equipStack(EquipmentSlot slot, ItemStack stack);

    protected SkeletonMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @ModifyConstant(method = "updateAttackType", constant = @Constant(intValue = 20))
    private static int interval(int x) {
        return 14;
    }

    @Inject(at = @At("TAIL"), method = "initEquipment")
    public void mixinInitEquipment(CallbackInfo ci) {
        if (random.nextFloat() < 0.8F) {
            int extraHP = random.nextInt(4);
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 999999, extraHP,false, false));
        }
        if (random.nextFloat() < (this.world.getDifficulty() == Difficulty.HARD ? 0.4F : 0.25F)) {
            int i = random.nextInt(8);
            int j = random.nextInt(5);
            int amp = random.nextInt(2);
            if (i == 0) {
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
                this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 999999, 0,false, false));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 999999, amp*3,false, false));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 999999, 1,false, false));
            } else if (i == 1) {
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
                this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 999999, amp * 2,false, false));
            } else if (i == 2) {
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
                this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 999999, amp * 2,false, false));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 999999, amp * 2,false, false));
            } else if (i == 3){
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
                this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 999999, amp * 2,false, false));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 999999, amp,false, false));
            } else if (i == 4) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 999999, amp,false, false));
            }

            int EnchantChance = random.nextInt(100);
            if (EnchantChance < 70) {
                this.equipStack(EquipmentSlot.MAINHAND, EnchantmentHelper.enchant(random, this.getMainHandStack(), EnchantChance, true));
            }

            if (j == 0) {
                this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 999999, amp,false, false));
            } else if (i >= 4 && j == 1) {
                this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 999999, amp,false, false));
            } else if (i>=4 && j == 2){
                this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 999999, amp,false, false));
            } else{
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 999999, amp,false, false));
            }
        }

    }

}


