package orangeVillager61.ImprovedVillagers;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {
	public static double VilPerDoor;
	public static boolean MateAgain;
	public static int twins;
	public static boolean enableDrops;
	public static int VillageDistance;
	public static boolean overwriteOriginalVillagers;
	public static boolean enableIvTexture;
	public static int adult_days;
	public static boolean enable_Metro;
	public static boolean use_spanish;
	public static boolean revertVillagers;
	
	public final static Config instance = new Config();
	
	public void load(FMLPreInitializationEvent event)
	{
	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
	config.load();
	twins = config.getInt("Twin Chance", Configuration.CATEGORY_GENERAL, 3, 0, 100, "Set this to the chance for the villagers to have twins. Set to 0 if you want to disable this.");
	VilPerDoor = config.get(Configuration.CATEGORY_GENERAL, "Villagers per Door", 1.0, "Set this to the number of villagers per door. Unmodded: 0.35(almost 3 doors for one villager), Mod Default: 1.0 (one door per villager)").getDouble(1.0);
	enableIvTexture = config.getBoolean("Enable Iv Texture", Configuration.CATEGORY_GENERAL, true, "Set this to true to enable this mod's villager texture, disable this if you don't like this mod's textures or you want resource packs to work with this mod's villagers.");
	MateAgain = config.getBoolean("Mate Again", Configuration.CATEGORY_GENERAL, true, "Set this to true if you want a chance(1/3) that villagers to be willing to mate again after mating.");
	enableDrops = config.getBoolean("Enable Villager Drops", Configuration.CATEGORY_GENERAL, true, "If this is set to true, villager drops will be enabled.");
	overwriteOriginalVillagers = config.getBoolean("Override Original Villagers?", Configuration.CATEGORY_GENERAL, true, "If this is set to true, then villagers will be overridden by the mod.");
	revertVillagers = config.getBoolean("Revert Back to Original Villagers", Configuration.CATEGORY_GENERAL, false, "If this is set to true, then villagers will be converted back to vanilla villagers. This setting will be considered more important then the override.");
	VillageDistance = config.getInt("Village Distance", Configuration.CATEGORY_GENERAL, 24, 2, 1024, "For each of this number of blocks, a village will try to spawn, Unmodded:32. This is will not do anything if Enable Villages is set to false");
	enable_Metro = config.getBoolean("Enable Cities", Configuration.CATEGORY_GENERAL, true, "If this is set to true, then there will be a 1/20 chance of cities (massive villages) will spawn. May be buggy");
	use_spanish = config.getBoolean("Use_Spanish", Configuration.CATEGORY_GENERAL, false, "If this is set to true, then villagers will be named with Hispanic names.");
	adult_days = config.getInt("Number of Minecraft Days as Adult", Configuration.CATEGORY_GENERAL, 45, 2, 1024, "This is the number of mc days that a villager will live, each life stage will take up 1/3 of the number of mc days set.");
	if (config.hasChanged())
	{
		config.save();
	}
	}
}

