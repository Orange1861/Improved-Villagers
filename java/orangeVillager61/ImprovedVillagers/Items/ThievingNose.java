package orangeVillager61.ImprovedVillagers.Items;

import net.minecraft.item.Item;
import orangeVillager61.ImprovedVillagers.Iv;

public class ThievingNose extends Item {
	
	public ThievingNose(String name, int maxstacksize){
		super();
	    this.setCreativeTab(Iv.tabIv);
	    this.setMaxStackSize(maxstacksize);
	    this.setUnlocalizedName(name);
	    this.setMaxDamage(12);
	}
}