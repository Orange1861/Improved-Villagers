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
		
		this.addSlotToContainer(new SlotItemHandler(handler, 0, 40, 66));
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 58, 66));
		this.addSlotToContainer(new SlotItemHandler(handler, 2, 76, 66));
		this.addSlotToContainer(new SlotItemHandler(handler, 3, 94, 66));
		this.addSlotToContainer(new SlotItemHandler(handler, 4, 112, 66));
		this.addSlotToContainer(new SlotItemHandler(handler, 5, 40, 84));
		this.addSlotToContainer(new SlotItemHandler(handler, 6, 58, 84));
		this.addSlotToContainer(new SlotItemHandler(handler, 7, 76, 84));
		this.addSlotToContainer(new SlotItemHandler(handler, 8, 94, 84));
		this.addSlotToContainer(new SlotItemHandler(handler, 9, 112, 84));
		this.addSlotToContainer(new SlotItemHandler(handler, 10, 40, 102));
		this.addSlotToContainer(new SlotItemHandler(handler, 11, 58, 102));
		this.addSlotToContainer(new SlotItemHandler(handler, 12, 76, 102));
		this.addSlotToContainer(new SlotItemHandler(handler, 13, 94, 102));
		this.addSlotToContainer(new SlotItemHandler(handler, 14, 112, 102));

		int xPos = 8;
		int yPos = 153;
				
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
