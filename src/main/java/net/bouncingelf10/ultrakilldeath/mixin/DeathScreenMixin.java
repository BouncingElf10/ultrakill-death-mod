package net.bouncingelf10.ultrakilldeath.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DeathScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DeathScreen.class)
public abstract class DeathScreenMixin {
	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void onDeathScreenRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		ci.cancel();
	}

	@Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
	private void disableMouseClick(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(false);
	}
}


