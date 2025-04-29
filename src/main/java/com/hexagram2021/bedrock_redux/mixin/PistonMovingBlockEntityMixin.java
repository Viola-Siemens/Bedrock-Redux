package com.hexagram2021.bedrock_redux.mixin;

import com.hexagram2021.bedrock_redux.entity.IOnGroundResetableEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PistonMovingBlockEntity.class)
public class PistonMovingBlockEntityMixin {
	@WrapOperation(method = "moveEntityByPiston", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V"))
	private static void bedrock_redux$resetTridentOnGround(Entity instance, MoverType type, Vec3 vec3, Operation<Void> original) {
		if(instance instanceof IOnGroundResetableEntity entity) {
			entity.bedrock_redux$resetOnGround();
			vec3 = vec3.add(0.0D, 0.0625D, 0.0D);
		}
		if(instance instanceof AbstractArrow arrow) {
			if (arrow.isNoPhysics()) {
				arrow.setYRot((float)(Mth.atan2(-vec3.x, -vec3.z) * (double)Mth.RAD_TO_DEG));
			} else {
				arrow.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)Mth.RAD_TO_DEG));
			}
			arrow.setXRot((float)(Mth.atan2(vec3.y, vec3.horizontalDistance()) * (double)Mth.RAD_TO_DEG));
			arrow.setXRot(bedrock_redux$lerpRotation(arrow.xRotO, arrow.getXRot()));
			arrow.setYRot(bedrock_redux$lerpRotation(arrow.yRotO, arrow.getYRot()));
			arrow.xRotO = arrow.getXRot();
			arrow.yRotO = arrow.getYRot();
			arrow.setDeltaMovement(arrow.getDeltaMovement().add(vec3.multiply(0.375D, 0.125D, 0.375D)));
		}
		original.call(instance, type, vec3);
	}

	@Unique
	private static float bedrock_redux$lerpRotation(float currentRotation, float targetRotation) {
		while(targetRotation - currentRotation < -180.0F) {
			currentRotation -= 360.0F;
		}

		while(targetRotation - currentRotation >= 180.0F) {
			currentRotation += 360.0F;
		}

		return Mth.lerp(0.5F, currentRotation, targetRotation);
	}
}
