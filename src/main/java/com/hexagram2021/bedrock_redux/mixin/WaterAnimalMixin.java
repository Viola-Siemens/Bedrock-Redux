package com.hexagram2021.bedrock_redux.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WaterAnimal.class)
public class WaterAnimalMixin {
	@ModifyVariable(method = "checkSurfaceWaterAnimalSpawnRules", at = @At(value = "STORE"), require = 0, ordinal = 0)
	private static int bedrock_redux$modifyBiggestYForSpawn(int original) {
		return Integer.MAX_VALUE;
	}

	@SuppressWarnings("deprecation")
	@ModifyVariable(method = "checkSurfaceWaterAnimalSpawnRules", at = @At(value = "STORE"), require = 0, ordinal = 1)
	private static int bedrock_redux$modifySmallestYForSpawn(int original, @Local(ordinal = 0, argsOnly = true) LevelAccessor level) {
		return level.getSeaLevel() - 14;
	}

	@WrapOperation(method = "checkSurfaceWaterAnimalSpawnRules", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
	private static boolean bedrock_redux$allowBubbleColumnSpawn(BlockState instance, Block block, Operation<Boolean> original) {
		return original.call(instance, block) || instance.is(Blocks.BUBBLE_COLUMN);
	}

	@WrapOperation(method = "checkSurfaceWaterAnimalSpawnRules", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"))
	private static boolean bedrock_redux$allowFlowingWaterSpawn(FluidState instance, TagKey<Fluid> fluid, Operation<Boolean> original) {
		return original.call(instance, fluid) || instance.is(Fluids.FLOWING_WATER);
	}
}
