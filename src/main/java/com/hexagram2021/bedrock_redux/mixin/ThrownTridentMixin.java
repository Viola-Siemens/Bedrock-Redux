package com.hexagram2021.bedrock_redux.mixin;

import com.hexagram2021.bedrock_redux.utils.DamageUtils;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ThrownTrident.class)
public class ThrownTridentMixin {
	@Shadow
	private ItemStack tridentItem;

	@ModifyExpressionValue(method = "findHitEntity", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/projectile/ThrownTrident;dealtDamage:Z"))
	private boolean bedrock_redux$alwaysTryHitEntity(boolean original) {
		return false;
	}

	@ModifyConstant(method = "onHitEntity", constant = @Constant(floatValue = 8.0F))
	private float bedrock_redux$addAttributeValue(float constant) {
		ThrownTrident current = (ThrownTrident)(Object)this;
		if(current.getOwner() instanceof LivingEntity livingEntity) {
			return DamageUtils.getAttributeWhenEquiping(Attributes.ATTACK_DAMAGE, livingEntity, this.tridentItem, EquipmentSlot.MAINHAND) - 1.0F;
		}
		return constant;
	}
}
