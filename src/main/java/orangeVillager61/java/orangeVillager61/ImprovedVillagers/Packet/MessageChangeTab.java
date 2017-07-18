package orangeVillager61.ImprovedVillagers.Packet;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageChangeTab implements IMessage {
	  // A default constructor is always required
	  public MessageChangeTab (){}

	  protected int entityID;
	  protected int button;
	  public MessageChangeTab (int toSend, int button) {
	    this.entityID = toSend;
	    this.button = button;
	  }

	  @Override public void toBytes(ByteBuf buf) {
	    // Writes the int into the buf
	    buf.writeInt(this.entityID);
	    buf.writeInt(this.button);
	  }

	  @Override public void fromBytes(ByteBuf buf) {
	    // Reads the int back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
		  entityID = buf.readInt();
		  button = buf.readInt();
	  }
	}