package net.bouncingelf10.ultrakilldeath.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.bouncingelf10.ultrakilldeath.ULTRAKILLDeathClient.isDead;

@Mixin(InGameHud.class)
public abstract class HUDMixin {

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void hideHUD(DrawContext context, float tickDelta, CallbackInfo ci) {
		if (isDead) {
			ci.cancel();
		}
	}
}


