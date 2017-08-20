package orangeVillager61.ImprovedVillagers.Blocks;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import orangeVillager61.ImprovedVillagers.Iv;
import orangeVillager61.ImprovedVillagers.Reference;

public class LightBlueStairs extends BlockStairs{
	public LightBlueStairs() {
		super(Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(3));
	    this.setCreativeTab(Iv.tabIv);
	    setRegistryName("light_blue_stairs");
	    setUnlocalizedName(Reference.MOD_ID + ":" + "light_blue_stairs");
	}

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
