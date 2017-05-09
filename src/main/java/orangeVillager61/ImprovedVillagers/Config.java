package orangeVillager61.ImprovedVillagers;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {
	public static double VilPerDoor;
	public static int MateAgain;
	public static int twins;
	public static int enableDrops;
	public static int enableVillages;
	public static int VillageDistance;
	public static int overwriteOriginalVillagers;
	
	public final static Config instance = new Config();
	
	public void load(FMLPreInitializationEvent event)
	{
	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
	config.load();
	twins = config.getInt("Twin Chance", Configuration.CATEGORY_GENERAL, 3, 0, 100, "Set this to the chance for the villagers to have twins. Set to 0 if you want to disable this.");
	VilPerDoor = config.get(Configuration.CATEGORY_GENERAL, "Villagers per Door", 1.0, "Set this to the number of villagers per door. Unmodded: 0.35(almost 3 doors for one villager), Mod Default: 1.0 (one door per villager)").getDouble(1.0);
	MateAgain = config.getInt("Mate Again", Configuration.CATEGORY_GENERAL, 0, 0, 1, "Set this to 0 if you want a chance(1/3) that villagers to be willing to mate again after mating.");
	enableDrops = config.getInt("Enable Villager Drops", Configuration.CATEGORY_GENERAL, 0, 0, 1, "If this is set to 0, villager drops will be enabled.");
	overwriteOriginalVillagers = config.getInt("Override Original Villagers?", Configuration.CATEGORY_GENERAL, 0, 0, 1, "If this is set to 0, then villagers will be overridden by the mod.");
	enableVillages = config.getInt("Enable Villages", Configuration.CATEGORY_GENERAL, 0, 0, 1, "If this is set to 0, this mod's villages will be enabled. Should fix village incompatibilities with other mods");
	VillageDistance = config.getInt("Village Distance", Configuration.CATEGORY_GENERAL, 24, 2, 128, "For each of this number of blocks, a village will try to spawn, Unmodded:32. This is will not do anything if Enable Villages is set to 1");
	if (config.hasChanged())
	{
		config.save();
	}
	}
}

