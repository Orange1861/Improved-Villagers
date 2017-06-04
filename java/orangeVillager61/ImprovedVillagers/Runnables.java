package orangeVillager61.ImprovedVillagers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;

public class Runnables {

	 public static Runnable run_hire_villager(MinecraftServer server, int EntityID, EntityPlayerMP player)
	 {
		 World world = server.getEntityWorld();
		 IvVillager villager = (IvVillager) world.getEntityByID(EntityID);
		 if (player.getDistanceToEntity(villager) <= 10)
		 {
			 villager.hire_Villager(player);
			 return null;
		 }
		return null;
	 }
	 public static Runnable run_change_follow(MinecraftServer server, int EntityID, EntityPlayerMP player)
	 {
		 World world = server.getEntityWorld();
		 IvVillager villager = (IvVillager) world.getEntityByID(EntityID);
		 if (player.getDistanceToEntity(villager) <= 10 && villager.getOwner().equals(player))
		 {
			 villager.change_following();
			 return null;
		 }
		return null;
	 }
}
