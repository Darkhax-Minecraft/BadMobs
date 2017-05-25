package net.darkhax.badmobs.addons.crt;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.darkhax.badmobs.BadMobs;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.BadMobs")
public class BadMobsTweaker {

    @ZenMethod
    public static void blacklist (int dimId, String entityId) {

        MineTweakerAPI.apply(new BlacklistDimensional(dimId, entityId));
    }

    @ZenMethod
    public static void blacklist (String entityId) {

        MineTweakerAPI.apply(new BlacklistGlobal(entityId));
    }

    public static class BlacklistGlobal implements IUndoableAction {

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

        @Override
        public void undo () {

            BadMobs.remove(this.entityId);
        }

        @Override
        public String describeUndo () {

            return "removing " + this.entityId + " globally";
        }

        @Override
        public Object getOverrideKey () {

            return null;
        }

        @Override
        public boolean canUndo () {

            return true;
        }
    }

    public static class BlacklistDimensional implements IUndoableAction {

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

        @Override
        public void undo () {

            BadMobs.remove(this.dimId, this.entityId);
        }

        @Override
        public String describeUndo () {

            return "removing " + this.entityId + " from dim " + this.dimId;
        }

        @Override
        public Object getOverrideKey () {

            return null;
        }

        @Override
        public boolean canUndo () {

            return true;
        }
    }
}
