package eu.pintergabor.earlytobed.item;


import net.minecraft.world.item.BucketItem;

import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class WoodenBucketItem
	extends BucketItem {

	// We must duplicate it, because it is private in BucketItem.
	protected final Fluid fluid;

	public WoodenBucketItem(Item.Properties settings) {
		this(Fluids.EMPTY, settings);
	}


	/**
	 * Create a wooden bucket
	 * @param fluid Fluids#EMPTY or Fluids#WATER
	 */
	public WoodenBucketItem(Fluid fluid, Item.Properties settings) {
		super(fluid, settings);
		this.fluid = fluid;
	}

	/**
	 * Fill empty bucket, if possible
	 * @param world World
	 * @param user Player
	 * @param itemStack ItemStack in Player's hand (=one empty wooden bucket)
	 * @param blockHitResult Block hit by player
	 * @return The usual {@link TypedActionResult} of {@link #use(World, PlayerEntity, Hand)}
	 */
//	protected TypedActionResult<ItemStack> fillEmptyBucket(
//		World world, PlayerEntity user, ItemStack itemStack, BlockHitResult blockHitResult) {
//		BlockPos blockHitPos = blockHitResult.getBlockPos();
//		BlockState blockState = world.getBlockState(blockHitPos);
//		Block block = blockState.getBlock();
//		if (world.canPlayerModifyAt(user, blockHitPos) && (block == Blocks.WATER)) {
//			// Try to empty it
//			FluidDrainable fluidDrainable = (FluidDrainable) block;
//			ItemStack emptiedStack = fluidDrainable.tryDrainFluid(user, world, blockHitPos, blockState);
//			// Normally it returns a WATER_BUCKET_ITEM
//			if (!emptiedStack.isEmpty()) {
//				// Change it to WOODEN_WATER_BUCKET_ITEM
//				emptiedStack = new ItemStack(ModItems.WOODEN_WATER_BUCKET_ITEM);
//				user.incrementStat(Stats.USED.getOrCreateStat(this));
//				fluidDrainable.getBucketFillSound().ifPresent(
//					(sound) -> user.playSound(sound, 1f, 1f));
//				world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockHitPos);
//				ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, user, emptiedStack);
//				if (!world.isClient) {
//					Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity) user, emptiedStack);
//				}
//				return TypedActionResult.success(itemStack3, world.isClient());
//			}
//		}
//		return TypedActionResult.fail(itemStack);
//	}

	/**
	 * Empty bucket, if possible
	 * @param world World
	 * @param user Player
	 * @param itemStack ItemStack in Player's hand (=one wooden bucket filled with water)
	 * @param blockHitResult Block hit by player
	 * @return The usual {@link TypedActionResult} of {@link #use(World, PlayerEntity, Hand)}
	 */
//	protected TypedActionResult<ItemStack> emptyBucket(
//		World world, PlayerEntity user, ItemStack itemStack, BlockHitResult blockHitResult) {
//		BlockPos blockHitPos = blockHitResult.getBlockPos();
//		Direction direction = blockHitResult.getSide();
//		BlockPos blockNextPos = blockHitPos.offset(direction);
//		BlockState blockState = world.getBlockState(blockHitPos);
//		BlockPos targetPos = (blockState.getBlock() instanceof FluidFillable) ? blockHitPos : blockNextPos;
//		if (user.canPlaceOn(blockNextPos, direction, itemStack) &&
//			placeFluid(user, world, targetPos, blockHitResult)) {
//			onEmptied(user, world, itemStack, targetPos);
//			if (user instanceof ServerPlayerEntity) {
//				Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity) user, targetPos, itemStack);
//			}
//			user.incrementStat(Stats.USED.getOrCreateStat(this));
//			return TypedActionResult.success(getEmptiedStack(itemStack, user), world.isClient());
//		}
//		return TypedActionResult.fail(itemStack);
//	}

	/**
	 * Use wooden bucket on a block
	 * @param world World
	 * @param pPlayer Player
	 * @param pHand Active hand
	 * @return The usual {@link InteractionResultHolder} values
	 */
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(
		@NotNull Level pLevel,
		@NotNull Player pPlayer,
		@NotNull InteractionHand pHand) {
		return super.use(pLevel, pPlayer, pHand);
	}
//	@Override
//	public InteractionResultHolder<ItemStack> use(
//		World world, PlayerEntity user, Hand hand) {
//		ItemStack itemStack = user.getStackInHand(hand);
//		BlockHitResult blockHitResult = net.minecraft.item.BucketItem.raycast(world, user,
//			fluid == Fluids.EMPTY ?
//				RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
//		if (blockHitResult.getType() == HitResult.Type.MISS) {
//			return TypedActionResult.pass(itemStack);
//		} else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
//			return TypedActionResult.pass(itemStack);
//		} else if (fluid == Fluids.EMPTY) {
//			return fillEmptyBucket(world, user, itemStack, blockHitResult);
//		}
//		return emptyBucket(world, user, itemStack, blockHitResult);
//	}

//	public static ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
//		if (!player.getAbilities().creativeMode) {
//			return new ItemStack(ModItems.WOODEN_BUCKET_ITEM);
//		}
//		return stack;
//	}
}

