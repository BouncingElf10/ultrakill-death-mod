package net.bouncingelf10.ultrakilldeath;


import foundry.veil.Veil;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.post.PostPipeline;
import foundry.veil.api.client.render.post.PostProcessingManager;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import foundry.veil.platform.VeilEventPlatform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.util.Identifier;

import static net.bouncingelf10.ultrakilldeath.ULTRAKILLDeath.LOGGER;
import static net.bouncingelf10.ultrakilldeath.ULTRAKILLDeath.MOD_ID;

public class ULTRAKILLDeathClient implements ClientModInitializer {

	private static float PROGRESS = 0.0f;

	@Override
	public void onInitializeClient() {
		LOGGER.info("initializing client " + MOD_ID);

		WorldRenderEvents.END.register((ctx) -> {
			try {
				ShaderActivator();
			} catch (Exception e) {
				LOGGER.error("An error occurred during world rendering", e);
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			if (client.world != null && client.player != null) {
				PROGRESS += 0.01f;
				if (PROGRESS > 1.0f) {
					PROGRESS = 0.0f;
				}
			}
		});
	}

	private void ShaderActivator() {
		try {
			PostProcessingManager postProcessingManager = VeilRenderSystem.renderer().getPostProcessingManager();
			PostPipeline postPipeline = postProcessingManager.getPipeline(Identifier.of(MOD_ID, "death"));
            assert postPipeline != null;
            postPipeline.setFloat("progress", PROGRESS);
			postProcessingManager.runPipeline(postPipeline);

		} catch (Exception e) {
            LOGGER.warn("Shader not found or failed to run. This is expected if the shader is not available. {}", e.getMessage());
		}
	}
}