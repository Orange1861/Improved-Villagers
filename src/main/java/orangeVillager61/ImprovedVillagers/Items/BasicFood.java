package orangeVillager61.ImprovedVillagers.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import orangeVillager61.ImprovedVillagers.Iv;
import orangeVillager61.ImprovedVillagers.Reference;

public class BasicFood extends ItemFood{
	
	private final PotionEffect[] effects;

	
	       
	public BasicFood(String name, int amount, float saturation, boolean isWolfFood, PotionEffect... effects) {
        super(amount, saturation, isWolfFood); 
        this.setCreativeTab(Iv.tabIv);
    	this.setMaxStackSize(64);
    	setUnlocalizedName(name);
    	//setRegistryName(Reference.MOD_ID + ":" + name);
        this.effects = effects;        
    }  
	
	public BasicFood(String name, int amount, float saturation, int maxstacksize, boolean isWolfFood, PotionEffect... effects) {
        super(amount, saturation, isWolfFood);
        this.setCreativeTab(Iv.tabIv);
    	this.setMaxStackSize(maxstacksize);
    	setUnlocalizedName(name);
    	//setRegistryName(Reference.MOD_ID + ":" + name);
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
}

