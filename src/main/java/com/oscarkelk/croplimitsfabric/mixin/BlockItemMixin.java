package com.oscarkelk.croplimitsfabric.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.Optional;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin extends Entity {

    public BlockItemMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/BlockItem;getBlockState()V"))
    private ActionResult place(ItemPlacementContext context) {
        Biome biome = context.getPlayer().getWorld().getBiome(context.getBlockPos());
        Optional<RegistryKey<Biome>> biomeKey = context.getPlayer().getWorld().getBiomeKey(context.getBlockPos());
        RegistryKey<World> registryKey = context.getPlayer().getEntityWorld().getRegistryKey();

        // Hot biomes
        if (biome.getTemperature() >= 1.0f) {
            if (context.getStack().getItem() == Items.CACTUS || context.getStack().getItem() == Items.BEETROOT_SEEDS || context.getStack().getItem() == Items.POTATO) {
                return ActionResult.SUCCESS;
            }
        }
        // Regular biomes
        if (biome.getTemperature() >= 0.15f && biome.getTemperature() <= 1.0f) {
            if (context.getStack().getItem() == Items.WHEAT_SEEDS || context.getStack().getItem() == Items.POTATO || context.getStack().getItem() == Items.PUMPKIN_SEEDS || context.getStack().getItem() == Items.SUGAR_CANE || context.getStack().getItem() == Items.MELON_SEEDS || context.getStack().getItem() == Items.SWEET_BERRIES) {
                return ActionResult.SUCCESS;
            }
        }
        // Cold biomes
        if (biome.getTemperature() <= 0.15f) {
            if (context.getStack().getItem() == Items.POTATO || context.getStack().getItem() == Items.PUMPKIN_SEEDS || context.getStack().getItem() == Items.SWEET_BERRIES) {
                return ActionResult.SUCCESS;
            }
        }
        // Jungle biomes
        if (biomeKey.equals(BiomeKeys.JUNGLE) || biomeKey.equals(BiomeKeys.BAMBOO_JUNGLE) || biomeKey.equals(BiomeKeys.SPARSE_JUNGLE)) {
            if (context.getStack().getItem() == Items.BAMBOO || context.getStack().getItem() == Items.COCOA_BEANS || context.getStack().getItem() == Items.POTATO || context.getStack().getItem() == Items.SUGAR_CANE || context.getStack().getItem() == Items.MELON_SEEDS) {
                return ActionResult.SUCCESS;
            }
        }
        // Nether Dimension
        if (registryKey == World.NETHER) {
            if (context.getStack().getItem() != Items.NETHER_WART) {
                return ActionResult.SUCCESS;
            }
        }
        else {
            return ActionResult.FAIL;
        }
        return ActionResult.SUCCESS;
    }
}

