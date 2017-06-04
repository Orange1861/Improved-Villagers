package orangeVillager61.ImprovedVillagers.Packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import orangeVillager61.ImprovedVillagers.Runnables;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;

//The params of the IMessageHandler are <REQ, REPLY>
//This means that the first param is the packet you are receiving, and the second is the packet you are returning.
//The returned packet can be used as a "response" from a sent packet.
public class HireVillagerPacket implements IMessageHandler<MessageSendEntityId, IMessage> {
// Do note that the default constructor is required, but implicitly defined in this case
	
	@Override public IMessage onMessage(MessageSendEntityId message, MessageContext ctx) {
		 // This is the player the packet was sent to the server from
	     EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
		 // The value that was sent
		 int amount = message.EntityID;
		 serverPlayer.mcServer.addScheduledTask(Runnables.run_hire_villager(ctx.getServerHandler().player.mcServer, message.EntityID, ctx.getServerHandler().player));
		 // No response packet
		 return null;
	}
}
