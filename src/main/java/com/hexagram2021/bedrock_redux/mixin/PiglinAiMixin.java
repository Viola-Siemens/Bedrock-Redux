package com.hexagram2021.bedrock_redux.mixin;

import com.hexagram2021.bedrock_redux.utils.PiglinUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PiglinAi.class)
public abstract class PiglinAiMixin {
	@Shadow
	private static void setAngerTargetToNearestTargetablePlayerIfFound(AbstractPiglin piglin, LivingEntity livingEntity) {
		throw new UnsupportedOperationException("Replaced by Mixin");
	}

	@Shadow
	protected static void setAngerTarget(AbstractPiglin piglin, LivingEntity livingEntity) {
		throw new UnsupportedOperationException("Replaced by Mixin");
	}

	@Inject(method = "angerNearbyPiglins", at = @At(value = "TAIL"))
	private static void bedrock_redux$stopAdmiringItems(Player player, boolean shouldSee, CallbackInfo ci, @Local(ordinal = 0) List<Piglin> list) {
		list.stream().filter(piglin -> piglin.getBrain().isActive(Activity.ADMIRE_ITEM))
				.filter(piglin -> !shouldSee || BehaviorUtils.canSee(piglin, player))
				.forEach(piglin -> {
					PiglinUtils.stopAdmiringItemsAndThrow(piglin);
					if (piglin.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
						setAngerTargetToNearestTargetablePlayerIfFound(piglin, player);
					} else {
						setAngerTarget(piglin, player);
					}
				});
	}
}
