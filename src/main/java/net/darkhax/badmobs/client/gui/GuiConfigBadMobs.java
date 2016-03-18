package net.darkhax.badmobs.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.badmobs.handler.ConfigurationHandler;
import net.darkhax.badmobs.lib.Constants;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class GuiConfigBadMobs extends GuiConfig {
    
    static Configuration cfg = ConfigurationHandler.config;
    static ConfigurationHandler cfgh;
    
    public GuiConfigBadMobs(GuiScreen parent) {
        
        super(parent, generateConfigList(), Constants.MODID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
    }
    
    /**
     * Generates a list of configuration options to be displayed in forge's configuration GUI.
     * 
     * @return List<IConfigElement>: A list of IConfigElement which are used to populate
     *         forge's configuration GUI.
     */
    public static List<IConfigElement> generateConfigList () {
        
        ArrayList<IConfigElement> elements = new ArrayList<IConfigElement>();
        
        for (String name : cfg.getCategoryNames())
            elements.add(new ConfigElement(cfg.getCategory(name)));
            
        return elements;
    }
}
