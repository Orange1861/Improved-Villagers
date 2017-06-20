package orangeVillager61.ImprovedVillagers;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class Reference {
	public static final String MOD_ID = "iv";
	public static final String MOD_NAME = "Improved Villagers";
	public static final String VERSION = "2.1.0";
	public static final SimpleNetworkWrapper PACKET_MODID = NetworkRegistry.INSTANCE.newSimpleChannel("iv");

}

