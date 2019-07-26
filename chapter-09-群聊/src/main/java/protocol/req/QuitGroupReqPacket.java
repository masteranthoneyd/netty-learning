package protocol.req;

import lombok.Data;
import protocol.Packet;

import static protocol.Command.QUIT_GROUP_REQ;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
@Data
public class QuitGroupReqPacket implements Packet {

    private String groupId;

    @Override
    public Byte supportCommand() {
        return QUIT_GROUP_REQ;
    }
}
