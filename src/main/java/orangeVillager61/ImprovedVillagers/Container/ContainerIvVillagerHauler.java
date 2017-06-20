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
		
		this.addSlotToContainer(new SlotItemHandler(handler, 0, 25, 23));
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 43, 23));
		this.addSlotToContainer(new SlotItemHandler(handler, 2, 61, 23));
		this.addSlotToContainer(new SlotItemHandler(handler, 3, 79, 23));
		this.addSlotToContainer(new SlotItemHandler(handler, 4, 97, 23));
		this.addSlotToContainer(new SlotItemHandler(handler, 5, 25, 41));
		this.addSlotToContainer(new SlotItemHandler(handler, 6, 43, 41));
		this.addSlotToContainer(new SlotItemHandler(handler, 7, 61, 41));
		this.addSlotToContainer(new SlotItemHandler(handler, 8, 79, 41));
		this.addSlotToContainer(new SlotItemHandler(handler, 9, 97, 41));
		this.addSlotToContainer(new SlotItemHandler(handler, 10, 25, 59));
		this.addSlotToContainer(new SlotItemHandler(handler, 11, 43, 59));
		this.addSlotToContainer(new SlotItemHandler(handler, 12, 61, 59));
		this.addSlotToContainer(new SlotItemHandler(handler, 13, 79, 59));
		this.addSlotToContainer(new SlotItemHandler(handler, 14, 97, 59));

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
		return villager.getDistanceToEntity(player) < 8;
	}
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
        ItemStack previous = null;
	    Slot slot = (Slot) this.inventorySlots.get(fromSlot);

	    if (slot != null && slot.getHasStack()) {
	        ItemStack current = slot.getStack();
	        previous = current.copy();

	        if (fromSlot < this.handler.getSlots()) {
	            // From the block breaker inventory to player's inventory
	            if (!this.mergeItemStack(current, handler.getSlots(), handler.getSlots() + 36, true))
	                return (ItemStack) null;
	        } else {
	            // From the player's inventory to block breaker's inventory
	            if (!this.mergeItemStack(current, 0, handler.getSlots(), false))
	                return (ItemStack) null;
	        }

	        if (current.stackSize == 0) //Use func_190916_E() instead of stackSize 1.11 only 1.11.2 use getCount()
	            slot.putStack((ItemStack) null); //Use ItemStack.field_190927_a instead of (ItemStack)null for a blank item stack. In 1.11.2 use ItemStack.EMPTY
	        else
	            slot.onSlotChanged();

	        if (current.stackSize == previous.stackSize)
	        slot.onPickupFromSlot(playerIn, current);
	    }
	    return previous;
	}
}
