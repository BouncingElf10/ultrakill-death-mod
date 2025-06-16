package net.bouncingelf10.ultrakilldeath.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.bouncingelf10.ultrakilldeath.ULTRAKILLDeathClient.isDead;

@Mixin(InGameOverlayRenderer.class)
public class GameOverlayMixin {

    @Inject(method = "renderFireOverlay", at = @At(value = "HEAD"), cancellable = true)
    private static void cancelFireTextureRender(MinecraftClient client, MatrixStack matrices, CallbackInfo ci) {
        if (isDead) {
            ci.cancel();
        }
    }
}

