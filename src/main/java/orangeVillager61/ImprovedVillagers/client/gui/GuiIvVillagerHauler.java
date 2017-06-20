package orangeVillager61.ImprovedVillagers.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import orangeVillager61.ImprovedVillagers.Reference;
import orangeVillager61.ImprovedVillagers.Container.ContainerIvVillagerHauler;
import orangeVillager61.ImprovedVillagers.Container.ContainerIvVillagerHireNitwit;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;
import orangeVillager61.ImprovedVillagers.Packet.MessageChangeFollow;

public class GuiIvVillagerHauler extends GuiContainer{

	private IvVillager villager;
	private IInventory playerInv;
	private String button_text;
	
	public GuiIvVillagerHauler(IvVillager villager, IInventory playerInv) {
		super(new ContainerIvVillagerHauler(villager, playerInv));
		
		this.xSize = 176;
		this.ySize = 166;
		
		this.villager = villager;
		this.playerInv = playerInv;
	}
	@Override
	public void initGui()
	{
		super.initGui();
		 if (this.villager.getFollowing()){
				this.button_text = "Following";
			}
			else 
			{
				this.button_text = "Follow";
			}
        this.addButton(new ButtonFollow(0, this.guiLeft + 118, this.guiTop + 20, 50, 20, this.button_text, this.villager));
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "gui/nitwit_tab.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	@Override
    protected void actionPerformed(GuiButton button)
    {
		if (this.villager.getHired())
		{ 
	    	Reference.PACKET_MODID.sendToServer(new MessageChangeFollow(this.villager.getEntityId()));
		}
		if (this.villager.getFollowing()){
			this.button_text = "Following";
		}
		else 
		{
			this.button_text = "Follow";
		}
    }
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = this.villager.getName();
        this.mc.fontRendererObj.drawString(s, this.xSize / 2 - this.mc.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.mc.fontRendererObj.drawString(this.playerInv.getDisplayName().getFormattedText(), 8, 72, 4210752); 
    }



}
