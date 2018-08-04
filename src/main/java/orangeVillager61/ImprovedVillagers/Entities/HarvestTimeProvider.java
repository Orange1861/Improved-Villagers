package orangeVillager61.ImprovedVillagers.Entities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class HarvestTimeProvider implements ICapabilitySerializable<NBTBase>{
	
	@CapabilityInject(IHarvestTime.class)
	public static final Capability<IHarvestTime> CAPABILITY_HARVEST_ANIMAL_TIME = null;
	
	private IHarvestTime instance = CAPABILITY_HARVEST_ANIMAL_TIME.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == CAPABILITY_HARVEST_ANIMAL_TIME;
	}
	 
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == CAPABILITY_HARVEST_ANIMAL_TIME ? CAPABILITY_HARVEST_ANIMAL_TIME.<T> cast(this.instance) : null;
	}
	 
	@Override
	public NBTBase serializeNBT()
	{
		return CAPABILITY_HARVEST_ANIMAL_TIME.getStorage().writeNBT(CAPABILITY_HARVEST_ANIMAL_TIME, this.instance, null);
	}
	
	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		CAPABILITY_HARVEST_ANIMAL_TIME.getStorage().readNBT(CAPABILITY_HARVEST_ANIMAL_TIME, this.instance, null, nbt);
	}
}
