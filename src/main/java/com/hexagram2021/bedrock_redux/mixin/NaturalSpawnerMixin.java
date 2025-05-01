package com.hexagram2021.bedrock_redux.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {
	@WrapOperation(method = "isValidEmptySpawnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;isEmpty()Z"))
	private static boolean bedrock_redux$ignoreOnlyOneBlockWater(FluidState instance, Operation<Boolean> original,
																 @Local(argsOnly = true) BlockGetter level, @Local(argsOnly = true) BlockPos pos,
																 @Local(argsOnly = true) EntityType<?> pEntityType) {
		return original.call(instance) || original.call(level.getFluidState(pos.above()));
	}
}
