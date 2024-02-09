package eu.pintergabor.earlytobed.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Sheep;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Inject code into SheepEntity#interactMob The code is identical to the original one, but checks for WOODEN_SHEARS_ITEM
 * instead of SHEARS
 */
@Mixin(Sheep.class)
public abstract class WoodenShearsMixin {
	@Inject(at = @At("HEAD"),
		method = "mobInteract",
		cancellable = true)
	private void interactMob(Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> cir) {
		Sheep that = (Sheep) (Object) this;
		ItemStack itemStack = pPlayer.getItemInHand(pHand);
//		if (itemStack.getItem() == WOODEN_SHEARS_ITEM) {
//			if (!that.wogetWorld().isClient && that.isShearable()) {
//				that.sheared(SoundCategory.PLAYERS);
//				that.emitGameEvent(GameEvent.SHEAR, pPlayer);
//				itemStack.damage(1, pPlayer, player -> player.sendToolBreakStatus(hand));
//				cir.setReturnValue(ActionResult.SUCCESS);
//			} else {
//				cir.setReturnValue(ActionResult.CONSUME);
//			}
//		}
	}
}
