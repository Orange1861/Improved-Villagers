package orangeVillager61.ImprovedVillagers.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import orangeVillager61.ImprovedVillagers.Iv;
import orangeVillager61.ImprovedVillagers.Reference;

public class BasicBlock extends Block {

    public BasicBlock(String name, Material material, float hardness, float resistance) {
    	super(material);
	    this.setCreativeTab(Iv.tabIv);
        this.setHardness(hardness);
        this.setResistance(resistance);
        setUnlocalizedName(name);
        setRegistryName(Reference.MOD_ID + ":" + name);
    }
    
}