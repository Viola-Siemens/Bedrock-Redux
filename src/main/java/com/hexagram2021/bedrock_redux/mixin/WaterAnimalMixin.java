package com.hexagram2021.bedrock_redux.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WaterAnimal.class)
public class WaterAnimalMixin {
	@ModifyVariable(method = "checkSurfaceWaterAnimalSpawnRules", at = @At(value = "STORE"), require = 0, ordinal = 0)
	private static int modifyBiggestYForSpawn(int original) {
		return Integer.MAX_VALUE;
	}

	@SuppressWarnings("deprecation")
	@ModifyVariable(method = "checkSurfaceWaterAnimalSpawnRules", at = @At(value = "STORE"), require = 0, ordinal = 1)
	private static int modifySmallestYForSpawn(int original, @Local(ordinal = 0, argsOnly = true) LevelAccessor level) {
		return level.getSeaLevel() - 14;
	}
}
