package orangeVillager61.ImprovedVillagers.Packet;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageHireVillager  implements IMessage {
	  // A default constructor is always required
	  public MessageHireVillager (){}

	  protected int entityID;
	  public MessageHireVillager (int toSend) {
	    this.entityID = toSend;
	  }

	  @Override public void toBytes(ByteBuf buf) {
	    // Writes the int into the buf
	    buf.writeInt(this.entityID);
	  }

	  @Override public void fromBytes(ByteBuf buf) {
	    // Reads the int back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
		  entityID = buf.readInt();
	  }
	}