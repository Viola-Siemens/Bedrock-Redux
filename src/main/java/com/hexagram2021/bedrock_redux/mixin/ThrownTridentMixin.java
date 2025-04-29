package com.hexagram2021.bedrock_redux.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.projectile.ThrownTrident;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ThrownTrident.class)
public class ThrownTridentMixin {
	@ModifyExpressionValue(method = "findHitEntity", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/projectile/ThrownTrident;dealtDamage:Z"))
	private boolean bedrock_redux$alwaysTryHitEntity(boolean original) {
		return false;
	}
}
