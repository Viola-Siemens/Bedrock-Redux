package com.hexagram2021.bedrock_redux.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {
	@WrapOperation(method = "isValidEmptySpawnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;isEmpty()Z"))
	private static boolean bedrock_redux$ignoreOnlyOneBlockWater(FluidState instance, Operation<Boolean> original,
																 @Local(argsOnly = true) BlockGetter level, @Local(argsOnly = true) BlockPos pos) {
		return original.call(instance) && original.call(level.getFluidState(pos.above()));
	}

	@WrapOperation(method = "canSpawnAtBody", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 0))
	private static boolean bedrock_redux$allowFlowingWaterSpawn(FluidState instance, TagKey<Fluid> fluid, Operation<Boolean> original) {
		return original.call(instance, fluid) || instance.is(Fluids.FLOWING_WATER);
	}
}
