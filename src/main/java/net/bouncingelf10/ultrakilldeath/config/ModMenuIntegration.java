package net.bouncingelf10.ultrakilldeath.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> createConfigScreen(parent);
    }

    private Screen createConfigScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.of("My Mod Config"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.of("General Settings"))
                        .tooltip(Text.of("Settings related to general behavior."))
                        .group(OptionGroup.createBuilder()
                                .name(Text.of("Main Options"))
                                .description(OptionDescription.of(Text.of("General toggle options.")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.of("Boolean Option"))
                                        .description(OptionDescription.of(Text.of("This text will appear as a tooltip when you hover over the option.")))
                                        .binding(
                                                true,
                                                () -> ULTRAKILLDeathConfig.INSTANCE.myBooleanOption,
                                                newVal -> ULTRAKILLDeathConfig.INSTANCE.myBooleanOption = newVal
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .build()
                                )
                                .build()
                        )
                        .build()
                )
                .save(() -> {

                })
                .build()
                .generateScreen(parent);
    }
}