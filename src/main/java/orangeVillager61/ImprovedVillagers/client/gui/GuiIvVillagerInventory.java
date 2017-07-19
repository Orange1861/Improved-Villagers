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
import orangeVillager61.ImprovedVillagers.Container.ContainerIvVillagerInventory;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;
import orangeVillager61.ImprovedVillagers.Packet.MessageChangeFollow;
import orangeVillager61.ImprovedVillagers.Packet.MessageChangeTab;

public class GuiIvVillagerInventory extends GuiContainer{

	private IvVillager villager;
	private IInventory playerInv;
	private String button_text;
	
	public GuiIvVillagerInventory(IvVillager villager, IInventory playerInv) {
		super(new ContainerIvVillagerInventory(villager, playerInv));
		
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
        this.addButton(new GuiButton(0, this.getGuiLeft() + 100, this.getGuiTop(), 50, 20, "Inventory"));
        if (this.villager.getProfession() == 5 && this.villager.getHired())
        {
            this.addButton(new GuiButton(0, this.getGuiLeft() + 50, this.getGuiTop(), 50, 20, "Hauler"));
        }
        else if (this.villager.getProfession() == 5 && !this.villager.getHired())
        {
            this.addButton(new GuiButton(0, this.getGuiLeft() + 50, this.getGuiTop(), 50, 20, "Hire"));
        }
        else if (!(this.villager.getProfession() == 5) && !this.villager.isChild())
        {
            this.addButton(new GuiButton(0, this.getGuiLeft() + 50, this.getGuiTop(), 50, 20, "Trade"));
        }
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "gui/nitwit_tab.png"));
		this.drawTexturedModalRect(this.getGuiLeft(), this.getGuiTop(), 0, 0, this.xSize, this.ySize);
	}
	@Override
    protected void actionPerformed(GuiButton button)
    {
		if (button.displayString.equals("Info"))
		{
	    	Reference.PACKET_MODID.sendToServer(new MessageChangeTab(this.villager.getEntityId(), 0));
		}
		else if (button.displayString.equals("Hire"))
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
    }
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = this.villager.getName();
        this.mc.fontRenderer.drawString(s, this.xSize / 2 - this.mc.fontRenderer.getStringWidth(s) / 2, 40, 4210752);
        this.mc.fontRenderer.drawString(this.playerInv.getDisplayName().getFormattedText(), 8, 138, 4210752);
        if (this.villager.getFollowing()){
			this.button_text = "Following";
		}
		else 
		{
			this.button_text = "Follow";
		}
    }



}
