package orangeVillager61.ImprovedVillagers.Entities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class VillagerProvider implements ICapabilitySerializable<NBTBase>

{

 @CapabilityInject(IVillagerStorage.class)

 public static final Capability<IVillagerStorage> VIL_CAP = null;



 private IVillagerStorage instance = VIL_CAP.getDefaultInstance();



 @Override

 public boolean hasCapability(Capability<?> capability, EnumFacing facing)

 {

 return capability == VIL_CAP;

 }



 @Override

 public <T> T getCapability(Capability<T> capability, EnumFacing facing)

 {

 return capability == VIL_CAP ? VIL_CAP.<T> cast(this.instance) : null;

 }



 @Override

 public NBTBase serializeNBT()

 {

 return VIL_CAP.getStorage().writeNBT(VIL_CAP, this.instance, null);

 }



 @Override

 public void deserializeNBT(NBTBase nbt)

 {

	 VIL_CAP.getStorage().readNBT(VIL_CAP, this.instance, null, nbt);

 }

}
