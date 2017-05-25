# BadMobs
A very simple minecraft mod which allows users to disable the spawning on any minecraft entity. This will also remove all existing entities of a banned type from that world.

[![Nodecraft](https://i.imgur.com/sz9PUmK.png)](https://nodecraft.com/r/darkhax)    
This project is sponsored by Nodecraft. Use code [Darkhax](https://nodecraft.com/r/darkhax) for 30% off your first month of service!

# Configuration
You can add blacklist entries through the generated config file. Each line in the config entry is a new blacklist entry. Just enter the save game id of the mob you want to blacklist. If you don't know the save game id of the mob, there is an item added by the mob which you can spawn in creative mode to get it. If you want to do a dimension specific blacklist entry, add a hashtag to the end followed by the dimension id. For example, `Squid#-1` will prevent the mob from spawning in the nether, but not in the surface world. 

# CraftTweaker Support
There is now craft tweaker support for this mod. You can use the following two lines in your CraftTweaker scripts!

- mods.BadMobs.blacklist(String saveGameId);
- mods.BadMobs.blacklist(int dimensionId, String saveGameId);
