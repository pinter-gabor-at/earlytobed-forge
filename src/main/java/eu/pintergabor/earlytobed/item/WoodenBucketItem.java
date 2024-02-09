package eu.pintergabor.earlytobed.item;


import static net.minecraftforge.registries.ForgeRegistries.FLUIDS;

import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class WoodenBucketItem
	extends BucketItem {

	// We must duplicate it, because it is private in BucketItem.
	protected final Fluid fluid;

	/**
	 * Create a wooden bucket
	 * @param fluid Fluids#EMPTY or Fluids#WATER
	 */
	public WoodenBucketItem(Fluid fluid, Item.Properties settings) {
		super(FLUIDS.getDelegateOrThrow(fluid), settings);
		this.fluid = fluid;
	}

	/**
	 * Fill empty bucket, if possible
	 * @param pLevel World
	 * @param pPlayer Player
	 * @param itemStack ItemStack in Player's hand (=one empty wooden bucket)
	 * @param blockHitResult Block hit by player
	 * @return The usual {@link InteractionResultHolder} values
	 */
	protected @NotNull InteractionResultHolder<ItemStack> fillEmptyBucket(
		@NotNull Level pLevel,
		@NotNull Player pPlayer,
		ItemStack itemStack,
		BlockHitResult blockHitResult) {
		BlockPos blockHitPos = blockHitResult.getBlockPos();
		if (!pLevel.mayInteract(pPlayer, blockHitPos)) {
			return InteractionResultHolder.pass(itemStack);
		}
		BlockState blockState = pLevel.getBlockState(blockHitPos);
		Block block = blockState.getBlock();
		if ((block instanceof BucketPickup bucketPickup) && (block == Blocks.WATER)) {
			ItemStack filledStack = bucketPickup.pickupBlock(pPlayer, pLevel, blockHitPos, blockState);
			// Normally it returns a WATER_BUCKET_ITEM
			if (!filledStack.isEmpty()) {
				// Change it to a WOODEN_WATER_BUCKET_ITEM
				filledStack = new ItemStack(ModItems.WOODEN_WATER_BUCKET_ITEM.get());
				pPlayer.awardStat(Stats.ITEM_USED.get(this));
				bucketPickup.getPickupSound(blockState).ifPresent((sound) -> {
					pPlayer.playSound(sound, 1f, 1f);
				});
				pLevel.gameEvent(pPlayer, GameEvent.FLUID_PICKUP, blockHitPos);
				// And this will change it to a WOODEN_BUCKET item
				ItemStack emptiedStack = ItemUtils.createFilledResult(itemStack, pPlayer, filledStack);
				if (!pLevel.isClientSide) {
					CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) pPlayer, filledStack);
				}
				return InteractionResultHolder.sidedSuccess(emptiedStack, pLevel.isClientSide());
			}
		}
		return InteractionResultHolder.fail(itemStack);
	}

	/**
	 * Empty bucket, if possible
	 * @param pLevel World
	 * @param pPlayer Player
	 * @param itemStack ItemStack in Player's hand (=one wooden bucket filled with water)
	 * @param blockHitResult Block hit by player
	 * @return The usual {@link InteractionResultHolder} values
	 */
	protected @NotNull InteractionResultHolder<ItemStack> emptyBucket(
		@NotNull Level pLevel,
		@NotNull Player pPlayer,
		ItemStack itemStack,
		BlockHitResult blockHitResult) {
		BlockPos blockHitPos = blockHitResult.getBlockPos();
		Direction direction = blockHitResult.getDirection();
		BlockPos blockNextPos = blockHitPos.relative(direction);
		if (!pLevel.mayInteract(pPlayer, blockHitPos) || !pPlayer.mayUseItemAt(blockNextPos, direction, itemStack)) {
			return InteractionResultHolder.pass(itemStack);
		}
		BlockState blockState = pLevel.getBlockState(blockHitPos);
		BlockPos targetPos = canBlockContainFluid(pLevel, blockHitPos, blockState) ? blockHitPos : blockNextPos;
		if (emptyContents(pPlayer, pLevel, targetPos, blockHitResult, itemStack)) {
			checkExtraContent(pPlayer, pLevel, itemStack, targetPos);
			if (pPlayer instanceof ServerPlayer) {
				CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) pPlayer, targetPos, itemStack);
			}
			pPlayer.awardStat(Stats.ITEM_USED.get(this));
			// Return a WOODEN_BUCKET_ITEM
			ItemStack emptiedStack = new ItemStack(ModItems.WOODEN_BUCKET_ITEM.get());
			return InteractionResultHolder.sidedSuccess(emptiedStack, pLevel.isClientSide());
		}
		return InteractionResultHolder.fail(itemStack);
	}

	/**
	 * Use wooden bucket on a block
	 * @param pLevel World
	 * @param pPlayer Player
	 * @param pHand Active hand
	 * @return The usual {@link InteractionResultHolder} values
	 */
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(
		@NotNull Level pLevel,
		@NotNull Player pPlayer,
		@NotNull InteractionHand pHand) {
		ItemStack itemStack = pPlayer.getItemInHand(pHand);
		BlockHitResult blockHitResult = getPlayerPOVHitResult(pLevel, pPlayer,
			fluid == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
		@SuppressWarnings("UnstableApiUsage") InteractionResultHolder<ItemStack> ret =
			ForgeEventFactory.onBucketUse(pPlayer, pLevel, itemStack, blockHitResult);
		if (ret != null) return ret;
		if (blockHitResult.getType() != HitResult.Type.BLOCK) {
			return InteractionResultHolder.pass(itemStack);
		}
		if (fluid == Fluids.EMPTY) {
			return fillEmptyBucket(pLevel, pPlayer, itemStack, blockHitResult);
		}
		return emptyBucket(pLevel, pPlayer, itemStack, blockHitResult);
	}
}

