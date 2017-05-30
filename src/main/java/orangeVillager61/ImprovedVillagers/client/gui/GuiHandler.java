package orangeVillager61.ImprovedVillagers.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import orangeVillager61.ImprovedVillagers.Container.ContainerIvVillagerHauler;
import orangeVillager61.ImprovedVillagers.Container.ContainerIvVillagerHireNitwit;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;

public class GuiHandler implements IGuiHandler{

	public static final int Villager_Hire = 0;
	public static final int Hauler = 1;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		AxisAlignedBB vilSearch = new AxisAlignedBB(pos);
		if (ID == Villager_Hire){
			return new ContainerIvVillagerHireNitwit(world.getEntitiesWithinAABB(IvVillager.class, vilSearch).get(0), player.inventory);
		}
		else if (ID == Hauler){
			return new ContainerIvVillagerHauler(world.getEntitiesWithinAABB(IvVillager.class, vilSearch).get(0), player.inventory);
		}
		else {
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		AxisAlignedBB vilSearch = new AxisAlignedBB(pos);
		if (ID == Villager_Hire)
		{
			return new GuiIvVillagerHireNitwit(world.getEntitiesWithinAABB(IvVillager.class, vilSearch).get(0), player.inventory, player);
		}
		else if (ID == Hauler){
			return new ContainerIvVillagerHauler(world.getEntitiesWithinAABB(IvVillager.class, vilSearch).get(0), player.inventory);
		}
		else {
			return null;
		}
	}

}
