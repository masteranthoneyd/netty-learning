package protocol.req;

import lombok.Data;
import lombok.experimental.Accessors;
import protocol.Packet;

import static protocol.Command.MESSAGE_REQ;

/**
 * @author ybd
 * @date 19-7-17
 * @contact yangbingdong1994@gmail.com
 */
@Data
@Accessors(chain = true)
public class MessageReqPacket implements Packet {

    private String toUserId;

    private String message;

    @Override
    public Byte supportCommand() {
        return MESSAGE_REQ;
    }
}
