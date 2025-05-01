package com.hexagram2021.bedrock_redux.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;

public final class SpawnUtils {
	public static boolean containsAnyLiquidIgnoreOneBlockWater(BlockGetter level, AABB aabb) {
		int minX = Mth.floor(aabb.minX);
		int maxX = Mth.ceil(aabb.maxX);
		int minY = Mth.floor(aabb.minY);
		int maxY = Mth.ceil(aabb.maxY);
		int minZ = Mth.floor(aabb.minZ);
		int maxZ = Mth.ceil(aabb.maxZ);
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

		for(int x = minX; x < maxX; ++x) {
			for(int y = minY; y < maxY; ++y) {
				for(int z = minZ; z < maxZ; ++z) {
					BlockState blockstate = level.getBlockState(pos.set(x, y, z));
					FluidState fluidState = blockstate.getFluidState();
					if (!fluidState.isEmpty()) {
						if(!fluidState.is(FluidTags.WATER) || y != minY) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	private SpawnUtils() {
	}
}
