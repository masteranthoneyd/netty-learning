package protocol.resp;

import lombok.Data;
import lombok.experimental.Accessors;
import protocol.Packet;

import static protocol.Command.MESSAGE_RESP;

/**
 * @author ybd
 * @date 19-7-17
 * @contact yangbingdong1994@gmail.com
 */
@Data
@Accessors(chain = true)
public class MessageRespPacket implements Packet {

    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte supportCommand() {
        return MESSAGE_RESP;
    }
}
