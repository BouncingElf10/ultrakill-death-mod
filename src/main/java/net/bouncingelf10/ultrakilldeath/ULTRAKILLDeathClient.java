package net.bouncingelf10.ultrakilldeath;


import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.post.PostPipeline;
import foundry.veil.api.client.render.post.PostProcessingManager;
import foundry.veil.api.client.util.Easings;
import net.bouncingelf10.ultrakilldeath.camera.FreeCamEntity;
import net.bouncingelf10.ultrakilldeath.keys.ULTRAKILLDeathKeyBindings;
import net.bouncingelf10.ultrakilldeath.mixin.SoundManagerAccessor;
import net.bouncingelf10.ultrakilldeath.mixin.SoundSystemAccessor;
import net.bouncingelf10.ultrakilldeath.sound.ULTRAKILLDeathSounds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.Map;

import static net.bouncingelf10.ultrakilldeath.ULTRAKILLDeath.LOGGER;
import static net.bouncingelf10.ultrakilldeath.ULTRAKILLDeath.MOD_ID;

public class ULTRAKILLDeathClient implements ClientModInitializer {

	public static float progress = 0.0f;
	public static float closingProgress = 0.0f;
	public static boolean isDead = false;
	private static int skullIndex = 0;
	private static long lastToggleTime = 0;
	public static boolean playedTVSound = false;
	private FreeCamEntity deathCam = null;
	private boolean switched = false;

	@Override
	public void onInitializeClient() {
		LOGGER.info("initializing client " + MOD_ID);

		ULTRAKILLDeathKeyBindings.register();

		WorldRenderEvents.END.register((ctx) -> {
			try {
				if (isDead) {
					ShaderActivator();
				}
			} catch (Exception e) {
				LOGGER.error("An error occurred during world rendering", e);
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			if (client.world != null && client.player != null && isDead) {
				progress += 0.025f;
				if (progress > 1.0f) {
					progress = 1.0f;
                }
				if (playedTVSound) {
					SoundManager soundManager = MinecraftClient.getInstance().getSoundManager();
					SoundSystem soundSystem = ((SoundManagerAccessor) soundManager).getSoundSystem();
					Map<SoundInstance, Source> sources = ((SoundSystemAccessor) soundSystem).getSources();

					for (SoundInstance instance : sources.keySet()) {
						Identifier id = instance.getId();
						if (!id.getNamespace().equals(MOD_ID)) {
							MinecraftClient.getInstance().getSoundManager().stop(instance);
						}
					}

				}
			}
		});

		WorldRenderEvents.START.register((ctx) -> {
			if (isDead) {
				if (progress >= 1.0f) {
					if (!playedTVSound) {
                        if (MinecraftClient.getInstance().player != null) {
							MinecraftClient.getInstance().getSoundManager().play(
									PositionedSoundInstance.master(ULTRAKILLDeathSounds.TV_ON, 1.0f)
							);

						} else {
							LOGGER.warn("Player not found, cannot play sound.");
						}
						playedTVSound = true;
					}
					closingProgress += 0.25f;
					if (closingProgress > 1.0f) {
						closingProgress = 1.0f;
					}
				}
			}

			MinecraftClient client = MinecraftClient.getInstance();

			if (client.world == null || client.player == null) return;
			ClientPlayerEntity player = client.player;

			if (player.isDead() && !switched) {
				Vec3d velocity = player.getVelocity();
				deathCam = new FreeCamEntity(client.world, player.getPos(), velocity, player.getPitch(), player.getYaw());
				client.setCameraEntity(deathCam);
				switched = true;
			}

			if (!player.isDead() && switched) {
				client.setCameraEntity(player);
				deathCam = null;
				switched = false;
			}

			if (deathCam != null) {
				deathCam.tick();
			}
		});
	}

	private void ShaderActivator() {
		try {
			long currentTime = System.nanoTime();
			long timeElapsed = currentTime - lastToggleTime;

			if (timeElapsed >= 600_000_000L) {
				if (MinecraftClient.getInstance().player != null) {
					if (skullIndex == 0 && playedTVSound) {
						MinecraftClient.getInstance().getSoundManager().play(
								PositionedSoundInstance.master(ULTRAKILLDeathSounds.SKULL_AHH, 1.0f)
						);

					}
				} else {
					LOGGER.warn("Player not found, cannot play sound.");
				}

				skullIndex = (skullIndex + 1) % 2;
				lastToggleTime = currentTime;
			}

			PostProcessingManager postProcessingManager = VeilRenderSystem.renderer().getPostProcessingManager();
			PostPipeline postPipeline = postProcessingManager.getPipeline(Identifier.of(MOD_ID, "death"));
            assert postPipeline != null;
			postPipeline.setFloat("progress", progress);
			postPipeline.setFloat("closingProgress", Easings.Easing.easeOutQuart.ease(closingProgress));
			postPipeline.setInt("skullIndex", skullIndex);

			postProcessingManager.runPipeline(postPipeline);

		} catch (Exception e) {
            LOGGER.warn("Shader not found or failed to run. {}", e.getMessage());
		}
	}
}