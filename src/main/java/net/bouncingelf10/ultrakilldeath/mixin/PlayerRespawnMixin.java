package net.bouncingelf10.ultrakilldeath.mixin;

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
    private void onPlayerRespawn(ServerPlayerEntity player, boolean alive, Entity.RemovalReason removalReason, CallbackInfoReturnable<ServerPlayerEntity> cir) {
        IS_DEAD = false;
        PROGRESS = 0.0f;
        CLOSING_PROGRESS = 0.0f;
    }
}
