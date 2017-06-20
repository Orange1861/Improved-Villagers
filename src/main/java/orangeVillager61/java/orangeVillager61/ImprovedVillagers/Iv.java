package orangeVillager61.ImprovedVillagers;

import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import orangeVillager61.ImprovedVillagers.Packet.MessageChangeFollow;
import orangeVillager61.ImprovedVillagers.Packet.MessageChangeFollowHandler;
import orangeVillager61.ImprovedVillagers.Packet.MessageHireVillager;
import orangeVillager61.ImprovedVillagers.Packet.MessageHireVillagerHandler;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, updateJSON = "https://raw.githubusercontent.com/Orange1861/update.json/master/update3.json", acceptedMinecraftVersions = "1.10.2-1.11.2")
public class Iv {
	
	@Instance
	public static Iv instance = new Iv();
		
	@SidedProxy(clientSide="orangeVillager61.ImprovedVillagers.ClientProxy", serverSide="orangeVillager61.ImprovedVillagers.CommonProxy")
	public static CommonProxy proxy;
		
	public final static IvTab tabIv = new IvTab("tabIv");
	
	public static final DamageSource dmgOldAge = new DamageSource("iv.old_age").setDamageBypassesArmor();

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
	   Config.instance.load(e);
	   proxy.preInit(e);
	   Reference.PACKET_MODID.registerMessage(MessageHireVillagerHandler.class, MessageHireVillager.class, 0, Side.SERVER);
	   Reference.PACKET_MODID.registerMessage(MessageChangeFollowHandler.class, MessageChangeFollow.class, 1, Side.SERVER);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
	    proxy.init(e);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	    proxy.postInit(e);
	}
};
