package com.rabbltman.evolve2.mixin;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DamageEnchantment.class)
public abstract class EnchantMixin8 extends Enchantment {
    private int typeIndex;

    protected EnchantMixin8(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes, int typeIndex) {
        super(weight, type, slotTypes);
        this.typeIndex = typeIndex;
    }

    /**
     * @author RabbltMan
     * @reason Damage Enchantments Improvement
     */
    @Overwrite
    public float getAttackDamage(int level, EntityGroup group) {
        if (this.typeIndex == 0) {
            return 1.0F + (float) Math.max(0, level - 1);
        } else if (this.typeIndex == 1) {
            return group == EntityGroup.UNDEAD ? (float)level * 2.5F : 0.5F + (float)level * 0.5F;
        } else {
            return this.typeIndex == 2 && group == EntityGroup.ARTHROPOD ? (float)level * 2.5F : (float)level * 0.5F;
        }
    }

    /**
     * @author RabbltMan
     * @reason Damage Enchantments Improvement
     */
    @Overwrite
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity livingEntity) {
            if (this.typeIndex == 2 && level > 0 && livingEntity.getGroup() == EntityGroup.ARTHROPOD) {
                int i = 20 + user.getRandom().nextInt(10 * level);
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, i, 3));
            }
            if (this.typeIndex == 1 && level > 0 && livingEntity.getGroup() == EntityGroup.UNDEAD) {
                int i = 20 + user.getRandom().nextInt(10 * level);
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, i, 3));
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, i, 5));
            }
        }
    }
}
