package net.bouncingelf10.ultrakilldeath.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Method;

import static net.bouncingelf10.ultrakilldeath.keys.ULTRAKILLDeathKeyBindings.*;

@Mixin(Screen.class)
public abstract class GlobalRespawnKeyMixin {

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof DeathScreen deathScreen && respawnKeyBinding.matchesKey(keyCode, -1)) {
            MinecraftClient.getInstance().player.requestRespawn();
            cir.setReturnValue(true);
        }

        if ((Object) this instanceof DeathScreen deathScreen && quitKeyBinding.matchesKey(keyCode, -1)) {
            ((DeathScreenAccessor) deathScreen).invokeOnTitleScreenButtonClicked();
            cir.setReturnValue(true);
        }
    }
}


