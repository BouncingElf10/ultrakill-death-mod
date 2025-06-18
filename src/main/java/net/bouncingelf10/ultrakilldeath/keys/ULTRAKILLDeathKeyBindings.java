package net.bouncingelf10.ultrakilldeath.keys;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ULTRAKILLDeathKeyBindings {

    public static KeyBinding respawnKeyBinding;
    public static KeyBinding quitKeyBinding;

    public static void register() {
        respawnKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ultrakilldeath.respawn",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.ultrakilldeath.ultrakilldeath"
        ));
        quitKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ultrakilldeath.quit",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.ultrakilldeath.ultrakilldeath"
        ));
    }
}
