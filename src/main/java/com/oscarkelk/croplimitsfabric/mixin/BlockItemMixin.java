package com.oscarkelk.croplimitsfabric.mixin;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {
//    @Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/BlockItem;getBlockState()V"))
    @Inject(method = "place", at = @At(value = "HEAD"), cancellable = true)
    private void place(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> ci) {
        Item item = context.getStack().getItem();
        Biome biome = context.getPlayer().getWorld().getBiome(context.getBlockPos());
        Category biomeCategory = context.getWorld().getBiomeAccess().getBiome(context.getBlockPos()).getCategory();
        RegistryKey<World> registryKey = context.getPlayer().getEntityWorld().getRegistryKey();

        if (item == Items.CACTUS) {
            if (biome.isHot(context.getBlockPos())) { // Hot biomes
                return;
            }
            ci.setReturnValue(ActionResult.FAIL);
        }
        if (item == Items.BEETROOT_SEEDS) {
            if (biome.isHot(context.getBlockPos())) { // Hot biomes
                return;
            }
            ci.setReturnValue(ActionResult.FAIL);
        }
        if (item == Items.WHEAT_SEEDS) {
            if (!biome.isCold(context.getBlockPos()) && !biome.isHot(context.getBlockPos())) { // Regular biomes
                return;
            }
            ci.setReturnValue(ActionResult.FAIL);
        }
        if (item == Items.PUMPKIN_SEEDS) {
            if (!biome.isHot(context.getBlockPos())) { // Regular and cold biomes
                return;
            }
            ci.setReturnValue(ActionResult.FAIL);
        }
        if (item == Items.SUGAR_CANE) {
            if (!biome.isCold(context.getBlockPos()) && !biome.isHot(context.getBlockPos()) || biomeCategory.equals(Category.JUNGLE)) { // Regular biomes and jungles
                return;
            }
            ci.setReturnValue(ActionResult.FAIL);
        }
        if (item == Items.MELON_SEEDS) {
            if (!biome.isCold(context.getBlockPos()) && !biome.isHot(context.getBlockPos()) || biomeCategory.equals(Category.JUNGLE)) { // Regular biomes and jungles
                return;
            }
            ci.setReturnValue(ActionResult.FAIL);
        }
        if (item == Items.SWEET_BERRIES) {
            if (!biome.isHot(context.getBlockPos())) { // Regular and cold biomes
                return;
            }
            ci.setReturnValue(ActionResult.FAIL);
        }
        if (item == Items.BAMBOO) {
            if (biomeCategory.equals(Category.JUNGLE)) {
                return;
            }
            ci.setReturnValue(ActionResult.FAIL);
        }
        if (item == Items.COCOA_BEANS) {
            if (biomeCategory.equals(Category.JUNGLE)) {
                return;
            }
            ci.setReturnValue(ActionResult.FAIL);
        }
        if (item == Items.NETHER_WART) {
            if (registryKey == World.NETHER) {
                return;
            }
            ci.setReturnValue(ActionResult.FAIL);
        }
    }
}

