package net.darkhax.badmobs.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = "badmobs", dist = Dist.CLIENT)
public class BadMobsClient {

    public BadMobsClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, (modContainer, parent) ->
                new ConfigurationScreen(modContainer, parent, BadMobsConfigurationSectionScreens.Mod::new)
        );
    }
}
