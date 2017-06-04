package orangeVillager61.ImprovedVillagers.Blocks;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import orangeVillager61.ImprovedVillagers.Iv;
import orangeVillager61.ImprovedVillagers.Reference;

public class LightBlueStairs extends BlockStairs{
	public LightBlueStairs(String name) {
		super(Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(3));
	    this.setCreativeTab(Iv.tabIv);
	    setUnlocalizedName(name);
	    setRegistryName(Reference.MOD_ID + ":" + name); 

	}
}
