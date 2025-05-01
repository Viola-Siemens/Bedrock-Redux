package com.hexagram2021.bedrock_redux.mixin;

import com.hexagram2021.bedrock_redux.utils.SpawnUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mob.class)
public class MobMixin {
	@WrapOperation(method = "checkSpawnObstruction", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelReader;containsAnyLiquid(Lnet/minecraft/world/phys/AABB;)Z"))
	private boolean bedrock_redux$ignoreOneBlockWater(LevelReader instance, AABB aabb, Operation<Boolean> original) {
		return SpawnUtils.containsAnyLiquidIgnoreOneBlockWater(instance, aabb);
	}
}
