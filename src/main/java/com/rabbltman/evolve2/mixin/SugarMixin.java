package com.rabbltman.evolve2.mixin;

import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;


@Mixin(Items.class)
public class SugarMixin {

    /**
     * @author RabbltMan
     * @reason evolve Minecraft
     */
    @Overwrite
    private static Item register(String id, Item item) {
        if (id.equals("sugar")) {
            item = new Item(new Item.Settings().food(new FoodComponent.Builder()
                            .alwaysEdible()
                            .snack()
                            .hunger(1)
                            .saturationModifier(2)
                            .build()));
        }

        if (item instanceof BlockItem) {
            ((BlockItem)item).appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return (Item) Registry.register(Registries.ITEM, new Identifier(id), item);
    }

}
