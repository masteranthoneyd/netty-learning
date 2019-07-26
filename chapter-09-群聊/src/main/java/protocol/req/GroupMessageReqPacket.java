package protocol.req;

import lombok.Data;
import lombok.experimental.Accessors;
import protocol.Packet;

import static protocol.Command.GROUP_MESSAGE_REQ;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
@Data
@Accessors(chain = true)
public class GroupMessageReqPacket implements Packet {
    private String groupId;

    private String message;
    @Override
    public Byte supportCommand() {
        return GROUP_MESSAGE_REQ;
    }
}
