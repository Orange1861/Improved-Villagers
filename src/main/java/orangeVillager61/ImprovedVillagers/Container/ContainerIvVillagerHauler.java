package orangeVillager61.ImprovedVillagers.Container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;

public class ContainerIvVillagerHauler extends Container{

	private IvVillager villager;
	
	private IItemHandler handler; 

	
	public ContainerIvVillagerHauler(IvVillager villager, IInventory playerInv){
		this.villager = villager;
		this.handler = this.villager.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		this.addSlotToContainer(new SlotItemHandler(handler, 0, 44, 23));
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 62, 23));
		this.addSlotToContainer(new SlotItemHandler(handler, 2, 80, 23));
		this.addSlotToContainer(new SlotItemHandler(handler, 3, 98, 23));
		this.addSlotToContainer(new SlotItemHandler(handler, 4, 116, 23));
		this.addSlotToContainer(new SlotItemHandler(handler, 5, 44, 41));
		this.addSlotToContainer(new SlotItemHandler(handler, 6, 62, 41));
		this.addSlotToContainer(new SlotItemHandler(handler, 7, 80, 41));
		this.addSlotToContainer(new SlotItemHandler(handler, 8, 98, 41));
		this.addSlotToContainer(new SlotItemHandler(handler, 9, 116, 41));
		this.addSlotToContainer(new SlotItemHandler(handler, 10, 44, 59));
		this.addSlotToContainer(new SlotItemHandler(handler, 11, 62, 59));
		this.addSlotToContainer(new SlotItemHandler(handler, 12, 80, 59));
		this.addSlotToContainer(new SlotItemHandler(handler, 13, 98, 59));
		this.addSlotToContainer(new SlotItemHandler(handler, 14, 116, 59));

		int xPos = 8;
		int yPos = 84;
				
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
			}
		}
				
		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(playerInv, x, xPos + x * 18, yPos + 58));
		}
	}
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
	    ItemStack previous = ItemStack.EMPTY;
	    Slot slot = (Slot) this.inventorySlots.get(fromSlot);

	    if (slot != null && slot.getHasStack()) {
	        ItemStack current = slot.getStack();
	        previous = current.copy();

	        if (fromSlot < this.handler.getSlots()) {
	            // From the block breaker inventory to player's inventory
	            if (!this.mergeItemStack(current, handler.getSlots(), handler.getSlots() + 36, true))
	                return ItemStack.EMPTY;
	        } else {
	            // From the player's inventory to block breaker's inventory
	            if (!this.mergeItemStack(current, 0, handler.getSlots(), false))
	                return ItemStack.EMPTY;
	        }

	        if (current.getCount() == 0) //Use func_190916_E() instead of stackSize 1.11 only 1.11.2 use getCount()
	            slot.putStack(ItemStack.EMPTY); //Use ItemStack.field_190927_a instead of (ItemStack)null for a blank item stack. In 1.11.2 use ItemStack.EMPTY
	        else
	            slot.onSlotChanged();

	        if (current.getCount() == previous.getCount())
	        slot.onTake(playerIn, current);
	    }
	    return previous;
	}
}
