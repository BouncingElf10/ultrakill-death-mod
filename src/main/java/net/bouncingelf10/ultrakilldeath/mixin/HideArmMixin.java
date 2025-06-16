package net.bouncingelf10.ultrakilldeath.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.bouncingelf10.ultrakilldeath.ULTRAKILLDeathClient.isDead;

@Mixin(HeldItemRenderer.class)
public class HideArmMixin {
	@Inject(method = "renderFirstPersonItem", at = @At("HEAD"), cancellable = true)
	private void hideArm(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
		if (isDead) {
			ci.cancel();
		}
	}
}



