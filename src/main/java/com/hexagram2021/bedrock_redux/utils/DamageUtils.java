package com.hexagram2021.bedrock_redux.utils;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.ItemStack;

public final class DamageUtils {
	public static float getAttributeWhenEquiping(Attribute attribute, LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot slot) {
		AttributeInstance old = livingEntity.getAttributes().getInstance(attribute);
		AttributeInstance instance = new AttributeInstance(attribute, ignored -> {});
		if(old == null) {
			instance.setBaseValue(0.0F);
		} else {
			instance.replaceFrom(old);
		}
		itemStack.getAttributeModifiers(slot).get(attribute).forEach(modifier -> {
			instance.removeModifier(modifier);
			instance.addTransientModifier(modifier);
		});
		return (float)instance.getValue();
	}

	private DamageUtils() {
	}
}
