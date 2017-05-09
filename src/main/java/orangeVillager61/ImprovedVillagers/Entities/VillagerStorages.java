package orangeVillager61.ImprovedVillagers.Entities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.Constants.NBT;

public class VillagerStorages implements IStorage<IVillagerStorage>

{
	 
	 @Override
	 public NBTBase writeNBT(Capability<IVillagerStorage> capability, IVillagerStorage instance, EnumFacing side)
	
	 {
		NBTTagCompound compound = new NBTTagCompound();
		if (instance.getGender() == 1 || instance.getGender() == 2){
			compound.setInteger("Gender", instance.getGender());
		}
		if (instance.getName() != null){
			compound.setString("Name", instance.getName());
		}
		return compound;
	 }



	 public void readNBT(Capability<IVillagerStorage> capability, IVillagerStorage instance, EnumFacing side, NBTBase nbt)
	
	 {
	 
	 instance.setName(((NBTTagCompound) nbt).getString("Name"));
	 instance.setGender(((NBTTagCompound) nbt).getInteger("Gender"));
	 }
}
