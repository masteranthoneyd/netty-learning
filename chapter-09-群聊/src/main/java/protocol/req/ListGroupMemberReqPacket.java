package protocol.req;

import lombok.Data;
import protocol.Packet;

import static protocol.Command.LIST_GROUP_MEMBER_REQ;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
@Data
public class ListGroupMemberReqPacket implements Packet {

    private String groupId;

    @Override
    public Byte supportCommand() {
        return LIST_GROUP_MEMBER_REQ;
    }
}
