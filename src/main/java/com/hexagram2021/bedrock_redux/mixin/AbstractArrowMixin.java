package com.hexagram2021.bedrock_redux.mixin;

import com.hexagram2021.bedrock_redux.entity.IOnGroundResetableEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin implements IOnGroundResetableEntity {
	@Shadow
	protected boolean inGround;
	@Shadow
	protected int inGroundTime;
	@Shadow private int life;

	@Shadow protected abstract void onHitEntity(EntityHitResult pResult);

	@Unique
	private boolean bedrock_redux$cacheReset = false;

	@WrapOperation(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;inGround:Z", ordinal = 0))
	private void bedrock_redux$skipResetOnGround(AbstractArrow instance, boolean value, Operation<Void> original) {
		if(instance instanceof IOnGroundResetableEntity entity && !entity.bedrock_redux$hasResetAndClearCache()) {
			original.call(instance, value);
		}
	}

	@Inject(method = "tickDespawn", at = @At(value = "HEAD"), cancellable = true)
	private void bedrock_redux$onlyDespawnWhenIdle(CallbackInfo ci) {
		if(this.inGroundTime < 5) {
			if(this.life > 0) {
				this.life -= 1;
			}
			ci.cancel();
		}
	}

	@Inject(method = "move", at = @At(value = "TAIL"))
	private void bedrock_redux$hurtWhenMoving(MoverType type, Vec3 pos, CallbackInfo ci) {
		AbstractArrow current = (AbstractArrow)(Object)this;
		current.level().getEntitiesOfClass(LivingEntity.class, current.getBoundingBox().expandTowards(current.getDeltaMovement()))
				.forEach(livingEntity -> this.onHitEntity(new EntityHitResult(livingEntity)));
	}

	@Override
	public void bedrock_redux$resetOnGround() {
		this.inGround = false;
		this.inGroundTime = 0;
		this.bedrock_redux$cacheReset = true;
	}

	@Override
	public boolean bedrock_redux$hasResetAndClearCache() {
		boolean ret = this.bedrock_redux$cacheReset;
		this.bedrock_redux$cacheReset = false;
		return ret;
	}
}
