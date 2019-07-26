package protocol.resp;

import lombok.Data;
import lombok.experimental.Accessors;
import protocol.Packet;
import session.Session;

import static protocol.Command.GROUP_MESSAGE_RESP;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
@Data
@Accessors(chain = true)
public class GroupMessageRespPacket implements Packet {

    private String message;

    private Session fromUser;

    private String fromGroupId;

    @Override
    public Byte supportCommand() {
        return GROUP_MESSAGE_RESP;
    }
}
