package orangeVillager61.ImprovedVillagers.Blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import orangeVillager61.ImprovedVillagers.Reference;

public class Villager_Nose extends BasicBlock{
	
	float f = 0.1F;

	protected final AxisAlignedBB AABB_NOSE = new AxisAlignedBB(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.3F, 0.5F + f);

	public Villager_Nose() {
		super("villager_nose", Material.CLAY, 1.0F, 0.25F);
	}
	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}
	@Override
	public boolean isFullCube(IBlockState state)
    {
        return false;
    }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
		return AABB_NOSE;
	}
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
