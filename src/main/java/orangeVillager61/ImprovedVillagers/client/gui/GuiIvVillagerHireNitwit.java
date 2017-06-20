package orangeVillager61.ImprovedVillagers.client.gui;

import java.io.IOException;

import com.jcraft.jogg.Packet;

import net.minecraft.client.gui.GuiButton;
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
import orangeVillager61.ImprovedVillagers.Packet.MessageHireVillager;

public class GuiIvVillagerHireNitwit extends GuiContainer{

	private IvVillager villager;
	private IInventory playerInv;
	private EntityPlayer player;
	protected int remaining_i = 0;
	
	public GuiIvVillagerHireNitwit(IvVillager villager, IInventory playerInv, EntityPlayer player) {
		super(new ContainerIvVillagerHireNitwit(villager, playerInv));
		
		this.xSize = 176;
		this.ySize = 166;
		this.player = player;
		
		this.villager = villager;
		this.playerInv = playerInv;

	}

	@Override
	public void initGui()
	{
		super.initGui();
        this.addButton(new GuiButton(0, this.guiLeft + 115, this.guiTop + 20, 50, 20, "Hire"));
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "gui/hire_nitwit.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
    protected void actionPerformed(GuiButton button)
    {
		final ItemStack stack = this.inventorySlots.getSlot(0).getStack();
		if (stack.stackSize >= villager.getHireCost() && stack.getItem() == Items.EMERALD){ 
        	Reference.PACKET_MODID.sendToServer(new MessageHireVillager(this.villager.getEntityId()));
        }
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = this.villager.getName();

        this.mc.fontRendererObj.drawString(String.valueOf(villager.getHireCost()), 46, 50, 4210752);
        this.mc.fontRendererObj.drawString(s, this.xSize / 2 - this.mc.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.mc.fontRendererObj.drawString(this.playerInv.getDisplayName().getFormattedText(), 8, 72, 4210752);
    }

}
