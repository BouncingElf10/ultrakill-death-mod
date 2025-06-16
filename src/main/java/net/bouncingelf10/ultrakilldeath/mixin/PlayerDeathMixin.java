package net.bouncingelf10.ultrakilldeath.mixin;

import net.bouncingelf10.ultrakilldeath.sound.ULTRAKILLDeathSounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.bouncingelf10.ultrakilldeath.ULTRAKILLDeathClient.*;
import static net.bouncingelf10.ultrakilldeath.ULTRAKILLDeath.LOGGER;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerDeathMixin {
	@Inject(method = "onDeath", at = @At("TAIL"))
	private void onDeath(DamageSource source, CallbackInfo ci) {
		isDead = true;
		progress = 0.0f;
		closingProgress = 0.0f;
		if (MinecraftClient.getInstance().player == null) {
			LOGGER.warn("Player not found, cannot play sound.");
			return;
		}
		MinecraftClient.getInstance().player.playSound(ULTRAKILLDeathSounds.DEATH_SEQUENCE, 1.0f, 1.0f);
	}
}


