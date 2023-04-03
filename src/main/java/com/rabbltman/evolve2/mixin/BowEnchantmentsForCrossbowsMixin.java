package com.rabbltman.evolve2.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;


@Mixin(CrossbowItem.class)
public abstract class BowEnchantmentsForCrossbowsMixin extends RangedWeaponItem {

    public BowEnchantmentsForCrossbowsMixin(Settings settings) {
        super(settings);
    }

    /**
     * @author RabbltMan
     * @reason Enchantment for bows -> bows and crossbows
     */
    @Overwrite
    private static PersistentProjectileEntity createArrow(World world, LivingEntity entity, ItemStack crossbow, ItemStack arrow) {
        ArrowItem arrowItem = (ArrowItem) (arrow.getItem() instanceof ArrowItem ? arrow.getItem() : Items.ARROW);
        PersistentProjectileEntity persistentProjectileEntity = arrowItem.createArrow(world, arrow, entity);
        if (entity instanceof PlayerEntity) {
            persistentProjectileEntity.setCritical(true);
        }

        persistentProjectileEntity.setSound(SoundEvents.ITEM_CROSSBOW_HIT);
        persistentProjectileEntity.setShotFromCrossbow(true);
        int i = EnchantmentHelper.getLevel(Enchantments.PIERCING, crossbow);
        if (i > 0) {
            persistentProjectileEntity.setPierceLevel((byte) i);
        }
        int j = EnchantmentHelper.getLevel(Enchantments.POWER, crossbow);
        if (j > 0) {
            persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage() + (double) j * 0.5 + 1.0);
        }

        int k = EnchantmentHelper.getLevel(Enchantments.PUNCH, crossbow);
        if (k > 0) {
            persistentProjectileEntity.setPunch(k);
        }

        if (EnchantmentHelper.getLevel(Enchantments.FLAME, crossbow) > 0) {
            persistentProjectileEntity.setOnFireFor(75);
        }
        return persistentProjectileEntity;
    }
}
