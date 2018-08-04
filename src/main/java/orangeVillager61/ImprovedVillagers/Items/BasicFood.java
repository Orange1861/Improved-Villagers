package orangeVillager61.ImprovedVillagers.Items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import orangeVillager61.ImprovedVillagers.Iv;
import orangeVillager61.ImprovedVillagers.Reference;

public class BasicFood extends ItemFood{
	
	private final PotionEffect[] effects;
	       
	public BasicFood(String name, int amount, float saturation, boolean isWolfFood, PotionEffect... effects) {
        super(amount, saturation, isWolfFood); 
        this.setCreativeTab(Iv.tabIv);
    	this.setMaxStackSize(64);
	    setRegistryName(name);
	    setUnlocalizedName(Reference.MOD_ID + ":" + name);
        this.effects = effects;        
    }  
	
	public BasicFood(String name, int amount, float saturation, int maxstacksize, boolean isWolfFood, PotionEffect... effects) {
        super(amount, saturation, isWolfFood);
        this.setCreativeTab(Iv.tabIv);
    	this.setMaxStackSize(maxstacksize);
	    setRegistryName(name);
	    setUnlocalizedName(Reference.MOD_ID + ":" + name);;
        this.effects = effects;        
    } 
	
	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
	    super.onFoodEaten(stack, world, player);
	        
	    for (int i = 0; i < effects.length; i ++) {
	        if (!world.isRemote && effects[i] != null)
	            player.addPotionEffect(new PotionEffect(this.effects[i]));
	    }
	}
	
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}

