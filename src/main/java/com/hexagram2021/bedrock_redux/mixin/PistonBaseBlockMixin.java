package com.hexagram2021.bedrock_redux.mixin;

import com.hexagram2021.bedrock_redux.utils.PiglinUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PistonBaseBlock.class)
public class PistonBaseBlockMixin {
	@SuppressWarnings("deprecation")
	@WrapOperation(method = "moveBlocks", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;updateNeighborsAt(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;)V", ordinal = 1))
	private void bedrock_redux$warnNearbyPiglins(Level instance, BlockPos blockPos, Block block, Operation<Void> original) {
		if(block.builtInRegistryHolder().is(BlockTags.GUARDED_BY_PIGLINS)) {
			instance.getEntitiesOfClass(Piglin.class, AABB.ofSize(blockPos.getCenter(), 16.0D, 16.0D, 16.0D)).forEach(PiglinUtils::stopAdmiringItemsAndThrow);
		}
		original.call(instance, blockPos, block);
	}
}
