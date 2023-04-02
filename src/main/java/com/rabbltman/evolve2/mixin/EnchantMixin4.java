package com.rabbltman.evolve2.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.MultishotEnchantment;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultishotEnchantment.class)
public abstract class EnchantMixin4 extends Enchantment{
    protected EnchantMixin4(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @Inject(at = @At("RETURN"), method = "canAccept", cancellable = true)
    public void canAcceptMixin(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(super.canAccept(other));
    }
}