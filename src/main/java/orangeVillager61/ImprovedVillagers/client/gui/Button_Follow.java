package orangeVillager61.ImprovedVillagers.client.gui;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.awt.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.items.CapabilityItemHandler;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;

public class Button_Follow extends GuiButton{
	
	protected IvVillager villager;
	
	public Button_Follow(int buttonId, int x, int y, int widthIn, int heightIn, IvVillager villager, String button_text) {
		super(buttonId, x, y, widthIn, heightIn, button_text);

		this.villager = villager;
		
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		int mouseX = (int) b.getX();
		int mouseY = (int) b.getY();
		if (this.mousePressed(mouseX, mouseY))
		{
			if (villager.getFollowing() == false){
				villager.setFollowing(true);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else if (villager.getFollowing() == true){
				villager.setFollowing(false);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public boolean mousePressed(int mouseX, int mouseY)
    {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

}
