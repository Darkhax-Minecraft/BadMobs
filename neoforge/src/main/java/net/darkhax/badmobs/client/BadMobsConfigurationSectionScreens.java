package net.darkhax.badmobs.client;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.minecraft.util.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.neoforged.fml.ModList;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface BadMobsConfigurationSectionScreens {

    class Mod extends ConfigurationScreen.ConfigurationSectionScreen {

        private final Map<String, MutableComponent> nameCache = new HashMap<>();

        public Mod(Screen parent, ModConfig.Type type, ModConfig modConfig, Component title) {
            super(parent, type, modConfig, title);
        }

        @Override
        protected @NonNull MutableComponent getTranslationComponent(@NonNull String key) {
            return this.nameCache.computeIfAbsent(key, k -> ModList.get().getModContainerById(k)
                    .map(container -> Component.literal(container.getModInfo().getDisplayName()))
                    .orElseGet(() -> super.getTranslationComponent(k)));
        }

        @Nullable
        @Override
        protected Element createSection(@NonNull String key, @NonNull UnmodifiableConfig subconfig, @NonNull UnmodifiableConfig subsection) {
            Map<String, Object> values = new HashMap<>();
            subconfig.entrySet().forEach(entry -> values.put(entry.getKey(), entry.getValue()));

            sectionCache.computeIfAbsent(key, k -> new Entity(
                    context, this, values, key, subsection.entrySet(), getTranslationComponent(k), k
            ));
            return super.createSection(key, subconfig, subsection);
        }
    }

    class Entity extends ConfigurationScreen.ConfigurationSectionScreen {

        private final String namespace;

        public Entity(Context parentContext, Screen parent, Map<String, Object> valueSpecs, String key,
                      Set<? extends UnmodifiableConfig.Entry> entrySet, Component title, String namespace) {
            super(parentContext, parent, valueSpecs, key, entrySet, title);
            this.namespace = namespace;
        }

        @Override
        protected @NonNull String getTranslationKey(@NonNull String key) {
            return Util.makeDescriptionId("entity", Identifier.fromNamespaceAndPath(this.namespace, key));
        }
    }
}
