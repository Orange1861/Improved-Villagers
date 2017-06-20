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

public class GuiIvVillagerInfo extends GuiContainer{

	private IvVillager villager;
	private IInventory playerInv;
	private EntityPlayer player;
	protected int remaining_i = 0;
	
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
        this.addButton(new GuiButton(0, this.guiLeft, this.guiTop, 50, 20, "Info"));
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "gui/info_gui.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
    protected void actionPerformed(GuiButton button)
    {

    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = this.villager.getName();
        String health_amount = "";
        String g = "None";
        if (this.villager.getGender() == 1)
        {
        	g = "Male";
        }
        else if (this.villager.getGender() == 2){
        	g = "Female";
        }
        this.mc.fontRendererObj.drawString("Name: " + s, 12, 30, 4210752);
        this.mc.fontRendererObj.drawString("Life Stage: " + this.villager.getAdultAge(), 12, 48, 4210752);
        this.mc.fontRendererObj.drawString("Gender: " + g, 12, 66, 4210752);
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
        this.mc.fontRendererObj.drawString("Health: " + health_amount + "(" + String.valueOf(this.villager.getHealth()) + ")", 12, 84, 4210752);
        if (!(this.villager.getMotherId() == null))
        {
            this.mc.fontRendererObj.drawString("Mother: " + this.villager.getMotherName(), 12, 102, 4210752);
        }
        if (!(this.villager.getFatherId() == null))
        {
            this.mc.fontRendererObj.drawString("Father: " + this.villager.getFatherName(), 12, 120, 4210752);
        }
        this.mc.fontRendererObj.drawString(this.playerInv.getDisplayName().getFormattedText(), 8, 138, 4210752);
    }

}
