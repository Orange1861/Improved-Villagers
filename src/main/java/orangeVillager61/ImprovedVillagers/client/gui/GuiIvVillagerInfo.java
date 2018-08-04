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
import orangeVillager61.ImprovedVillagers.Packet.MessageChangeTab;
import orangeVillager61.ImprovedVillagers.Packet.MessageHireVillager;

public class GuiIvVillagerInfo extends GuiContainer{

	private IvVillager villager;
	private IInventory playerInv;
	
	public GuiIvVillagerInfo(IvVillager villager, IInventory playerInv) {
		super(new ContainerIvVillagerHireNitwit(villager, playerInv));
		
		this.xSize = 176;
		this.ySize = 234;
		
		this.villager = villager;
		this.playerInv = playerInv;

	}

	@Override
	public void initGui()
	{
		super.initGui();
        this.addButton(new GuiButton(0, this.getGuiLeft(), this.getGuiTop(), 50, 20, "Info"));
        //this.addButton(new GuiButton(0, this.getGuiLeft() + 100, this.getGuiTop(), 50, 20, "Inventory"));
        if (this.villager.getProfession() == 5 && this.villager.getHired())
        {
            this.addButton(new GuiButton(0, this.getGuiLeft() + 50, this.getGuiTop(), 50, 20, "Hauler"));
        }
        else if (this.villager.getProfession() == 5 && !this.villager.getHired())
        {
            this.addButton(new GuiButton(0, this.getGuiLeft() + 50, this.getGuiTop(), 50, 20, "Hire"));
        }
        if (!(this.villager.getProfession() == 5) && !this.villager.isChild())
        {
            this.addButton(new GuiButton(0, this.getGuiLeft() + 50, this.getGuiTop(), 50, 20, "Trade"));
        }
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "gui/info_gui.png"));
		this.drawTexturedModalRect(this.getGuiLeft(), this.getGuiTop(), 0, 0, this.xSize, this.ySize);
	}
	
	@Override
    protected void actionPerformed(GuiButton button)
    {
		if (button.displayString.equals("Hire"))
		{
	    	Reference.PACKET_MODID.sendToServer(new MessageChangeTab(this.villager.getEntityId(), 1));
		}
		else if (button.displayString.equals("Hauler"))
		{
	    	Reference.PACKET_MODID.sendToServer(new MessageChangeTab(this.villager.getEntityId(), 2));
		}
		else if (button.displayString.equals("Trade"))
		{
	    	Reference.PACKET_MODID.sendToServer(new MessageChangeTab(this.villager.getEntityId(), 3));
		}
		else if (button.displayString.equals("Inventory"))
		{
	    	Reference.PACKET_MODID.sendToServer(new MessageChangeTab(this.villager.getEntityId(), 4));
		}
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = this.villager.getName();
        String health_amount = "";
        String g = "None";
        String life_stage = this.villager.getAdultAge();
        if (this.villager.isChild())
        {
        	life_stage = "Child";
        }
        if (this.villager.getGender() == 1)
        {
        	g = "Male";
        }
        else if (this.villager.getGender() == 2){
        	g = "Female";
        }
        this.mc.fontRenderer.drawString("Name: " + s, 12, 30, 4210752);
        this.mc.fontRenderer.drawString("Life Stage: " + life_stage, 12, 48, 4210752);
        this.mc.fontRenderer.drawString("Gender: " + g, 12, 66, 4210752);
        if (this.villager.getMaxHealth() * 0.75 <= this.villager.getHealth())
        {
        	health_amount = "Slightly Wounded";
        }
        else if (this.villager.getMaxHealth() * 0.55 <= this.villager.getHealth())
        {
        	health_amount = "Minor Injury";
        }
        else if (this.villager.getMaxHealth() * 0.40 <= this.villager.getHealth())
        {
        	health_amount = "Moderate Injury";
        }
        else if (this.villager.getMaxHealth() * 0.25 <= this.villager.getHealth())
        {
        	health_amount = "Weak";
        }
        else if (this.villager.getMaxHealth() * 0.14 <= this.villager.getHealth())
        {
        	health_amount = "Critically wounded";
        }
        else if (this.villager.getMaxHealth() * 0.14 > this.villager.getHealth())
        {
        	health_amount = "Near Death";
        }
        if (this.villager.getMaxHealth() == this.villager.getHealth())
        {
        	health_amount = "Perfectly Healthy";
        }
        this.mc.fontRenderer.drawString("Health: " + health_amount + "(" + String.valueOf(this.villager.getHealth()) + ")", 12, 84, 4210752);
        if (!(this.villager.getMotherId() == null))
        {
            this.mc.fontRenderer.drawString("Mother: " + this.villager.getMotherName(), 12, 102, 4210752);
        }
        if (!(this.villager.getFatherId() == null))
        {
            this.mc.fontRenderer.drawString("Father: " + this.villager.getFatherName(), 12, 120, 4210752);
        }
        this.mc.fontRenderer.drawString(this.playerInv.getDisplayName().getFormattedText(), 8, 138, 4210752);
    }

}
