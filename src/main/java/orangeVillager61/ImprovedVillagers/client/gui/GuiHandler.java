package orangeVillager61.ImprovedVillagers.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import orangeVillager61.ImprovedVillagers.Container.ContainerIvVillagerHauler;
import orangeVillager61.ImprovedVillagers.Container.ContainerIvVillagerHireNitwit;
import orangeVillager61.ImprovedVillagers.Container.ContainerIvVillagerInfo;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;

public class GuiHandler implements IGuiHandler{

	public static final int Villager_Hire = 0;
	public static final int Hauler = 1;
	public static final int Info = 2;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// x is used as the entity ID for entity-based GUIs, suggested by Choonster
		if (ID == Villager_Hire){
			return new ContainerIvVillagerHireNitwit((IvVillager) world.getEntityByID(x), player.inventory);
		}
		else if (ID == Hauler){
			return new ContainerIvVillagerHauler((IvVillager) world.getEntityByID(x), player.inventory);		
			}
		else if (ID == Info)
		{
			return new ContainerIvVillagerInfo((IvVillager) world.getEntityByID(x), player.inventory);		
		}
		else {
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// x is used as the entity ID for entity-based GUIs, suggested by Choonster
		if (ID == Villager_Hire)
		{
			return new GuiIvVillagerHireNitwit((IvVillager) world.getEntityByID(x), player.inventory, player);
		}
		else if (ID == Hauler){
			return new GuiIvVillagerHauler((IvVillager) world.getEntityByID(x), player.inventory);
		}
		else if (ID == Info){
			return new GuiIvVillagerInfo((IvVillager) world.getEntityByID(x), player.inventory);
		}
		else {
			return null;
		}
	}

}
