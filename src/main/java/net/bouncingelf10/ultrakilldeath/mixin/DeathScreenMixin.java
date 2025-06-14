package net.bouncingelf10.ultrakilldeath.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.bouncingelf10.ultrakilldeath.ULTRAKILLDeathClient.IS_DEAD;
import static net.bouncingelf10.ultrakilldeath.ULTRAKILLDeathClient.PROGRESS;

@Mixin(DeathScreen.class)
public abstract class DeathScreenMixin {
	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void onDeathScreenRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		ci.cancel();
	}
}


