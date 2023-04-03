package com.rabbltman.evolve2.mixin;

import net.minecraft.enchantment.ImpalingEnchantment;
import net.minecraft.entity.EntityGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ImpalingEnchantment.class)
public abstract class EnchantMixin2 {

    @Inject(at = @At("RETURN"), method = "getAttackDamage", cancellable = true)
    public void getAttackDamageMixin(int level, EntityGroup group, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(group == EntityGroup.AQUATIC ? (float)level * 2.5F : (float)level * 1.5F);
    }
}
