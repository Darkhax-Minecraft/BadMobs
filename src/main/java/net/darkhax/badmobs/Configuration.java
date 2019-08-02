package net.darkhax.badmobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class Configuration {

    public static final String REMOVE_NAMED = "removeNamed";
    public static final String BANNED_MOBS = "bannedMobs";
    public static final String ADD_TOOLTIP = "addTooltip";

    private final ForgeConfigSpec spec;
    private final ConfigValue<List<? extends String>> globalIds;
    private final BooleanValue displayTooltip;
    
    public Configuration () {

        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        // General Configs
        builder.comment("General settings for the mod.");
        builder.push("general");

        builder.comment("A list of all banned mobs. If a mobs entity id is added to this list, it will not be allowed to spawn in any world.");
        builder.translation("badmobs.config.banned");
        this.globalIds = builder.defineList(BANNED_MOBS, new ArrayList<String>(), val -> val instanceof String);
        builder.pop();

        // Client
        builder.comment("Client only settings.");
        builder.push("client");

        builder.comment("Displays the ID of mobs on their spawn egg tooltip while enabled.");
        builder.translation("badmobs.config.addtooltip");
        displayTooltip = builder.define(ADD_TOOLTIP, false);
        builder.pop();
        
        this.spec = builder.build();
    }
    
    public ForgeConfigSpec getSpec() {
    	
    	return this.spec;
    }

    public boolean addTooltip () {

        return this.displayTooltip.get();
    }

    public List<? extends String> getBannedMobs () {

        return this.globalIds.get();
    }
}