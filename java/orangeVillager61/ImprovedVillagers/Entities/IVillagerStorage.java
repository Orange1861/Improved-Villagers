package orangeVillager61.ImprovedVillagers.Entities;

import net.minecraft.network.datasync.DataParameter;

public interface IVillagerStorage {

	 public void setName(String name);

	 public void setGender(int gender);

	 public String getName();

	 public int getGender();
}

