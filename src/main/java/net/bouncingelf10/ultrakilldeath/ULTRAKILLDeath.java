package net.bouncingelf10.ultrakilldeath;

import net.bouncingelf10.ultrakilldeath.sound.ULTRAKILLDeathSounds;
import net.fabricmc.api.ModInitializer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ULTRAKILLDeath implements ModInitializer {
	public static final String MOD_ID = "ultrakilldeath";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("initializing " + MOD_ID);
		ULTRAKILLDeathSounds.registerSounds();
	}
}