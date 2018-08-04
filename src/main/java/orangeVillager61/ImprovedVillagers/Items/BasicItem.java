package orangeVillager61.ImprovedVillagers.Items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import orangeVillager61.ImprovedVillagers.Iv;
import orangeVillager61.ImprovedVillagers.Reference;

public class BasicItem extends Item {
	
	public BasicItem(String name, int maxstacksize){
		super();
	    this.setCreativeTab(Iv.tabIv);
	    this.setMaxStackSize(maxstacksize);
	    setRegistryName(name);
	    setUnlocalizedName(Reference.MOD_ID + ":" + name);
	}
	public BasicItem(String name, int maxstacksize, int maxDamage){
		super();
	    this.setCreativeTab(Iv.tabIv);
	    this.setMaxStackSize(maxstacksize);
	    setRegistryName(name);
	    setUnlocalizedName(Reference.MOD_ID + ":" + name);
	    this.setMaxDamage(maxDamage);
	}
	public BasicItem (String name) {
		super();
	    this.setCreativeTab(Iv.tabIv);
	    this.setMaxStackSize(64);
	    setRegistryName(name);
	    setUnlocalizedName(Reference.MOD_ID + ":" + name);
	}

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
