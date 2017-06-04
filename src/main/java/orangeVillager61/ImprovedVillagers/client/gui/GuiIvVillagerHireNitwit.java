package orangeVillager61.ImprovedVillagers.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import orangeVillager61.ImprovedVillagers.Reference;
import orangeVillager61.ImprovedVillagers.Blocks.IvBlocks;
import orangeVillager61.ImprovedVillagers.Container.ContainerIvVillagerHireNitwit;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;

public class GuiIvVillagerHireNitwit extends GuiContainer{

	private IvVillager villager;
	private IInventory playerInv;
	private EntityPlayer player;
	
	public GuiIvVillagerHireNitwit(IvVillager villager, IInventory playerInv, EntityPlayer player) {
		super(new ContainerIvVillagerHireNitwit(villager, playerInv));
		
		this.xSize = 176;
		this.ySize = 166;
		this.player = player;
		
		this.villager = villager;
		this.playerInv = playerInv;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "gui/hire_nitwit.png"));
		this.drawTexturedModalRect(this.getGuiLeft(), this.getGuiTop(), 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = this.villager.getName();
        int i = 0;
        Boolean has_emeralds;
        this.mc.fontRenderer.drawString(String.valueOf(villager.getHireCost()), 46, 50, 4210752);
        this.mc.fontRenderer.drawString(s, this.xSize / 2 - this.mc.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.mc.fontRenderer.drawString(this.playerInv.getDisplayName().getFormattedText(), 8, 72, 4210752);
        if (this.inventorySlots.getSlot(0).getStack().getCount() >= villager.getHireCost() && this.inventorySlots.getSlot(0).getStack().getItem().equals(Items.EMERALD)){ //this is where the container needs to be accessed
        	has_emeralds = true;
        	if (this.inventorySlots.getSlot(0).getStack().getCount() > villager.getHireCost())
        	{
        		i = this.inventorySlots.getSlot(0).getStack().getCount() - villager.getHireCost();
        	}
        }
        else 
        {
        	has_emeralds = false;
        }
        this.addButton(new Button_Hire(0, this.getGuiLeft() + 115, this.getGuiTop() + 20, 50, 25, "Hire", this.villager, has_emeralds, this.player, i));
    }

}
