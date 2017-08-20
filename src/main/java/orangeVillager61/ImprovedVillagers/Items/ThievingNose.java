package orangeVillager61.ImprovedVillagers.Items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import orangeVillager61.ImprovedVillagers.Iv;
import orangeVillager61.ImprovedVillagers.Reference;

public class ThievingNose extends Item {
	
	public ThievingNose(){
		super();
	    this.setCreativeTab(Iv.tabIv);
	    setRegistryName("thieving_nose");
	    setUnlocalizedName(Reference.MOD_ID + ":" + "thieving_nose");
	    this.setMaxDamage(12);
	    this.setMaxStackSize(16);
	}
	
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}