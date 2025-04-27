package com.hexagram2021.bedrock_redux.utils;

import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;

public final class PiglinUtils {
	public static void stopAdmiringItemsAndThrow(Piglin piglin) {
		if (PiglinAi.isHoldingItemInOffHand(piglin)) {
			PiglinAi.stopHoldingOffHandItem(piglin, true);
		}

		Brain<Piglin> brain = piglin.getBrain();
		brain.eraseMemory(MemoryModuleType.CELEBRATE_LOCATION);
		brain.eraseMemory(MemoryModuleType.DANCING);
		brain.eraseMemory(MemoryModuleType.ADMIRING_ITEM);

		PiglinAi.getAvoidTarget(piglin).ifPresent(livingEntity -> brain.eraseMemory(MemoryModuleType.AVOID_TARGET));
	}

	private PiglinUtils() {
	}
}
