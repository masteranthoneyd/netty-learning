package protocol.req;

import lombok.Data;
import protocol.Packet;

import static protocol.Command.JOIN_GROUP_REQ;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
@Data
public class JoinGroupReqPacket implements Packet {

    private String groupId;

    @Override
    public Byte supportCommand() {
        return JOIN_GROUP_REQ;
    }
}
