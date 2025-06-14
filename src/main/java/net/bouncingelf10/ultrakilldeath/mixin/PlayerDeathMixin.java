package net.bouncingelf10.ultrakilldeath.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.player.PlayerEntity;
import static net.bouncingelf10.ultrakilldeath.ULTRAKILLDeathClient.*;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerDeathMixin {
	@Inject(method = "onDeath", at = @At("HEAD"))
	private void onDeath(DamageSource source, CallbackInfo ci) {
		IS_DEAD = true;
		PROGRESS = 0.0f;
		CLOSING_PROGRESS = 0.0f;
	}
}


