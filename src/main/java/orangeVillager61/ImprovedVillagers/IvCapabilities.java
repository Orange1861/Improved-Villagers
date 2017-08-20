package orangeVillager61.ImprovedVillagers;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;
import orangeVillager61.ImprovedVillagers.Entities.HarvestTimeUser;
import orangeVillager61.ImprovedVillagers.Entities.IHarvestTime;

public class IvCapabilities {

	public static void regsiterCapabilties()
	{
		CapabilityManager.INSTANCE.register(IHarvestTime.class, new IHarvestTimeStorage(), HarvestTimeUser.class);
	}
	
	public static class IHarvestTimeStorage implements IStorage<IHarvestTime>
	{

		@Override
		 public NBTBase writeNBT(Capability<IHarvestTime> capability, IHarvestTime instance, EnumFacing side)
		{
		return new NBTTagLong (instance.get_time());
		}
		
		@Override
		public void readNBT(Capability<IHarvestTime> capability, IHarvestTime instance, EnumFacing side, NBTBase nbt)
		{
		instance.setTime(((NBTPrimitive) nbt).getLong());
		}
	}
}
