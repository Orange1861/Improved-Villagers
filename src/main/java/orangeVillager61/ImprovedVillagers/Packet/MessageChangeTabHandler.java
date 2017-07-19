package orangeVillager61.ImprovedVillagers.Packet;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;

//The params of the IMessageHandler are <REQ, REPLY>
//This means that the first param is the packet you are receiving, and the second is the packet you are returning.
//The returned packet can be used as a "response" from a sent packet.
public class MessageChangeTabHandler implements IMessageHandler<MessageChangeTab, IMessage> {
// Do note that the default constructor is required, but implicitly defined in this case

@Override public IMessage onMessage(MessageChangeTab message, MessageContext ctx) {
 // This is the player the packet was sent to the server from
 EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
 // The value that was sent
 serverPlayer.mcServer.addScheduledTask(new Runnable_Change_Tab(message, ctx));
 // No response packet
 return null;
}
}
class Runnable_Change_Tab implements Runnable{
	
	private IvVillager villager;
	private int entityID;
	private int button;
	private EntityPlayerMP player;
	private World world;

	 public Runnable_Change_Tab(MessageChangeTab message, MessageContext ctx)
	 {
	     this.player = ctx.getServerHandler().player;
		 this.entityID = message.entityID;
		 this.world = this.player.world;
		 this.button = message.button;
	 }
	@Override
	public void run() {
		this.villager = (IvVillager) world.getEntityByID(this.entityID);
		if (this.player.getDistanceToEntity(this.villager) <= 10)
		{
			this.villager.change_tab(this.button, this.player);
		}
	}
}
