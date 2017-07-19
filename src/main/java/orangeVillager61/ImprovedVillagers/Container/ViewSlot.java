package orangeVillager61.ImprovedVillagers.Container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ViewSlot extends Slot{

	public ViewSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

    public boolean canTakeStack(EntityPlayer playerIn)
    {
        return false;
    }
}
