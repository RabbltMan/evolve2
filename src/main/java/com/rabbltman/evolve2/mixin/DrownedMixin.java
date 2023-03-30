package com.rabbltman.evolve2.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrownedEntity.class)
public abstract class DrownedMixin extends LivingEntity {
    protected DrownedMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "initEquipment")
    public void mixinInitEquipment(CallbackInfo ci) {
        if ((double) random.nextFloat() < 0.5) {
            int i = random.nextInt(16);
            if (i < 8) {
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.TRIDENT));
            } else {
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
            }
            if (random.nextInt(16) < 10) {
                this.equipStack(EquipmentSlot.MAINHAND, EnchantmentHelper.enchant(random, this.getMainHandStack(),30, true));
            }
        }
    }
}
