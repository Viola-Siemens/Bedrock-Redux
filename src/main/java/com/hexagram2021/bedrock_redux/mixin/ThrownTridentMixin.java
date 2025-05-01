package com.hexagram2021.bedrock_redux.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ThrownTrident;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ThrownTrident.class)
public class ThrownTridentMixin {
	@ModifyExpressionValue(method = "findHitEntity", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/projectile/ThrownTrident;dealtDamage:Z"))
	private boolean bedrock_redux$alwaysTryHitEntity(boolean original) {
		return false;
	}

	@ModifyConstant(method = "onHitEntity", constant = @Constant(floatValue = 8.0F))
	private float bedrock_redux$addAttributeValue(float constant) {
		ThrownTrident current = (ThrownTrident)(Object)this;
		float adder = 0.0F;
		if(current.getOwner() instanceof LivingEntity livingEntity) {
			adder = (float)livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE) - 1.0F;
			if(adder < -1.0F) {
				adder = -1.0F;
			}
		}
		return constant + adder;
	}
}
