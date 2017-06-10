package orangeVillager61.ImprovedVillagers.client.gui;

import java.lang.reflect.Method;
import java.awt.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;

public class Button_Hire extends GuiButton{
	
	public IvVillager villager;
	
	
	public Button_Hire(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, IvVillager villager, EntityPlayer player, int remaining_i) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		int mouseX = (int) b.getX();
		int mouseY = (int) b.getY();
	}
	public boolean mousePressed(int mouseX, int mouseY)
    {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

}
