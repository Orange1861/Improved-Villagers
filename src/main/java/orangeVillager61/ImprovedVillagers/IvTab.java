package orangeVillager61.ImprovedVillagers;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IvTab extends CreativeTabs{

	public IvTab(String label) {
		super(label);
	}

	@Override
	public Item getTabIconItem() {
		return Items.EMERALD;
	}

}

