package net.bouncingelf10.ultrakilldeath.mixin;

import net.minecraft.client.gui.screen.DeathScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DeathScreen.class)
public interface DeathScreenAccessor {
    @Invoker("onTitleScreenButtonClicked")
    void invokeOnTitleScreenButtonClicked();
}


