package orangeVillager61.ImprovedVillagers;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import orangeVillager61.ImprovedVillagers.Blocks.IvBlocks;
import orangeVillager61.ImprovedVillagers.Items.IvItems;

public class IvRecipes {
	@SideOnly(Side.CLIENT)
	 public static void applyEntityIdToItemStack(ItemStack stack, String entityId)
	 {
	     NBTTagCompound nbttagcompound = new net.minecraft.nbt.NBTTagCompound();
	     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
	     nbttagcompound1.setString("id", entityId);
	     nbttagcompound.setTag("EntityTag", nbttagcompound1);
	     stack.setTagCompound(nbttagcompound); 
	 }
	 
	 public static void addRecipes() {
		 GameRegistry.addShapelessRecipe(new ItemStack (IvItems.thieving_nose), Items.EMERALD, IvBlocks.villager_nose);
		 GameRegistry.addShapelessRecipe(new ItemStack (IvItems.notification_marker), Items.EMERALD, new ItemStack (Items.IRON_INGOT, 2));
		 GameRegistry.addRecipe(new ItemStack(IvItems.notification_marker), new Object[]
	    		  {" E ", 
	    		   " I ", 
	    		   " I ", Character.valueOf('E'), Items.EMERALD, Character.valueOf('I'), Items.IRON_INGOT});
		 GameRegistry.addRecipe(new ItemStack(IvBlocks.light_blue_stairs), new Object[]
	    		  {"B  ", 
	    		   "BB ", 
	    		   "BBB", Character.valueOf('B'), new ItemStack (Blocks.STAINED_HARDENED_CLAY, 1, 3)});
		 GameRegistry.addSmelting(IvItems.raw_villager, new ItemStack(IvItems.cooked_villager), 0.35F);
	 }
}
