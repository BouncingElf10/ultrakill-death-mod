package net.bouncingelf10.ultrakilldeath.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.bouncingelf10.ultrakilldeath.ULTRAKILLDeathClient.*;

@Mixin(PlayerManager.class)
public class PlayerRespawnMixin {
    @Inject(method = "respawnPlayer", at = @At("TAIL"))
    private void onPlayerRespawn(ServerPlayerEntity player, boolean alive, CallbackInfoReturnable<ServerPlayerEntity> cir) {
        isDead = false;
        progress = 0.0f;
        closingProgress = 0.0f;
        playedTVSound = false;
        MinecraftClient.getInstance().getSoundManager().stopAll();
    }
}
