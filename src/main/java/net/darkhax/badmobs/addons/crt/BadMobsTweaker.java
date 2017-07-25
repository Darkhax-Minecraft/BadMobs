package net.darkhax.badmobs.addons.crt;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import net.darkhax.badmobs.BadMobs;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.BadMobs")
public class BadMobsTweaker {

    @ZenMethod
    public static void blacklist (int dimId, String entityId) {

        CraftTweakerAPI.apply(new BlacklistDimensional(dimId, entityId));
    }

    @ZenMethod
    public static void blacklist (String entityId) {

        CraftTweakerAPI.apply(new BlacklistGlobal(entityId));
    }

    public static class BlacklistGlobal implements IAction {

        private final String entityId;

        public BlacklistGlobal (String entityId) {

            this.entityId = entityId;
        }

        @Override
        public void apply () {

            BadMobs.blacklist(this.entityId);
        }

        @Override
        public String describe () {

            return "blacklisting " + this.entityId + " globally";
        }
    }

    public static class BlacklistDimensional implements IAction {

        private final int dimId;
        private final String entityId;

        public BlacklistDimensional (int dimId, String entityId) {

            this.dimId = dimId;
            this.entityId = entityId;
        }

        @Override
        public void apply () {

            BadMobs.blacklist(this.dimId, this.entityId);
        }

        @Override
        public String describe () {

            return "blacklisting " + this.entityId + " from dim " + this.dimId;
        }
    }
}
