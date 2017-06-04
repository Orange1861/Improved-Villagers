package orangeVillager61.ImprovedVillagers.Blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import orangeVillager61.ImprovedVillagers.Reference;

public class Villager_Nose extends BasicBlock{
	
	float f = 0.1F;

	protected final AxisAlignedBB AABB_NOSE = new AxisAlignedBB(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.3F, 0.5F + f);

	public Villager_Nose(String name, Material material, float hardness, float resistance) {
		super(name, material, hardness, resistance);
		setUnlocalizedName(name);
		//setRegistryName(Reference.MOD_ID + ":" + name);
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


}
