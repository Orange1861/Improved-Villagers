package orangeVillager61.ImprovedVillagers.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import orangeVillager61.ImprovedVillagers.Iv;
import orangeVillager61.ImprovedVillagers.Reference;

public class BasicItem extends Item {
	
	public BasicItem(String name, int maxstacksize){
		super();
	    this.setCreativeTab(Iv.tabIv);
	    this.setMaxStackSize(maxstacksize);
	    setUnlocalizedName(name);
	    //setRegistryName(Reference.MOD_ID + ":" + name);
	}
	
	public BasicItem (String name) {
		super();
	    this.setCreativeTab(Iv.tabIv);
	    this.setMaxStackSize(64);
	    setUnlocalizedName(name);
	    //setRegistryName(Reference.MOD_ID + ":" + name);
	}

}
