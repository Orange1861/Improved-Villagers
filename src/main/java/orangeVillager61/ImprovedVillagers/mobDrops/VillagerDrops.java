package orangeVillager61.ImprovedVillagers.mobDrops;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import orangeVillager61.ImprovedVillagers.Config;
import orangeVillager61.ImprovedVillagers.Blocks.IvBlocks;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;
import orangeVillager61.ImprovedVillagers.Items.IvItems;

public class VillagerDrops{

    public Random r = new Random();

    @SubscribeEvent(priority=EventPriority.LOW, receiveCanceled=true)
	public void onEvent(LivingDropsEvent event)
	{
    	if (Config.enableDrops == 0){
		    if (event.getEntity() instanceof EntityVillager || event.getEntity() instanceof IvVillager)
		    {
		        ItemStack itemStackToDrop1 = new ItemStack(Items.EMERALD,  r.nextInt(3) + 1);
		        ItemStack itemStackToDrop2 = new ItemStack(Items.BONE,  r.nextInt(1));
		        if (event.getEntity().isBurning()){
		        ItemStack itemStackToDrop4 = new ItemStack(IvItems.cooked_villager,  r.nextInt(2) + 1);
		        event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, 
			        	  event.getEntity().posY, event.getEntity().posZ, itemStackToDrop4));
		        }
		        else if (!event.getEntity().isBurning()){
			        ItemStack itemStackToDrop4 = new ItemStack(IvItems.raw_villager,  r.nextInt(2) + 1);
			        event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, 
				        	  event.getEntity().posY, event.getEntity().posZ, itemStackToDrop4));
		        }
		        ItemStack itemStackToDrop3 = new ItemStack(IvBlocks.villager_nose,  r.nextInt(1) + 1);
		        event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, 
			        	  event.getEntity().posY, event.getEntity().posZ, itemStackToDrop1));
		        event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, 
			        	  event.getEntity().posY, event.getEntity().posZ, itemStackToDrop2));
		        event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, 
			        	  event.getEntity().posY, event.getEntity().posZ, itemStackToDrop3));
		        
		    }
		    
		} 
	}
}
