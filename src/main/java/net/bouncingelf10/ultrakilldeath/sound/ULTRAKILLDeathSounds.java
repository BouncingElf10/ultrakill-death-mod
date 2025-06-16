package net.bouncingelf10.ultrakilldeath.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static net.bouncingelf10.ultrakilldeath.ULTRAKILLDeath.*;

public class ULTRAKILLDeathSounds {

    public static final SoundEvent SKULL_AHH = registerSoundEvent("skull_ahh");
    public static final SoundEvent DEATH_SEQUENCE = registerSoundEvent("death_sequence");
    public static final SoundEvent TV_ON = registerSoundEvent("tv_on");
    public static final SoundEvent TV_STATIC = registerSoundEvent("tv_static");


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        LOGGER.info("Registering {} sounds", MOD_ID);
    }
}
