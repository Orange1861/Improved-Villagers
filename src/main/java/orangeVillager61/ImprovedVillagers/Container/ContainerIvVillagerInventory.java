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

public class ContainerIvVillagerInventory extends Container{

	private IvVillager villager;
	
	private IInventory handler; 

	
	public ContainerIvVillagerInventory(IvVillager villager, IInventory playerInv){
		this.villager = villager;
		this.handler = this.villager.getVillagerInventory();
		
		this.addSlotToContainer(new ViewSlot(handler, 0, 40, 66));
		this.addSlotToContainer(new ViewSlot(handler, 1, 58, 66));
		this.addSlotToContainer(new ViewSlot(handler, 2, 76, 66));
		this.addSlotToContainer(new ViewSlot(handler, 3, 94, 66));
		this.addSlotToContainer(new ViewSlot(handler, 4, 112, 66));
		this.addSlotToContainer(new ViewSlot(handler, 5, 40, 84));
		this.addSlotToContainer(new ViewSlot(handler, 6, 58, 84));
		this.addSlotToContainer(new ViewSlot(handler, 7, 76, 84));
		this.addSlotToContainer(new ViewSlot(handler, 8, 94, 84));
		this.addSlotToContainer(new ViewSlot(handler, 9, 112, 84));
		this.addSlotToContainer(new ViewSlot(handler, 10, 40, 102));
		this.addSlotToContainer(new ViewSlot(handler, 11, 58, 102));
		this.addSlotToContainer(new ViewSlot(handler, 12, 76, 102));
		this.addSlotToContainer(new ViewSlot(handler, 13, 94, 102));
		this.addSlotToContainer(new ViewSlot(handler, 14, 112, 102));
		this.addSlotToContainer(new ViewSlot(handler, 15, 40, 120));
		this.addSlotToContainer(new ViewSlot(handler, 16, 58, 120));
		this.addSlotToContainer(new ViewSlot(handler, 17, 76, 120));
		this.addSlotToContainer(new ViewSlot(handler, 18, 94, 120));
		this.addSlotToContainer(new ViewSlot(handler, 19, 112, 120));

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
		return true;

	}
	
    protected ViewSlot addSlotToContainer(ViewSlot slotIn)
    {
        slotIn.slotNumber = this.inventorySlots.size() + 1;
        this.inventorySlots.add(slotIn);
        //this.inventoryItemStacks.add(ItemStack.EMPTY);
        return slotIn;
    }
}
