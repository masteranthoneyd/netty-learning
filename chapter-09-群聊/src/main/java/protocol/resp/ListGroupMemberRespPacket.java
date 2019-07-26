package protocol.resp;

import lombok.Data;
import protocol.Packet;
import session.Session;

import java.util.List;

import static protocol.Command.LIST_GROUP_MEMBER_RESP;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
@Data
public class ListGroupMemberRespPacket implements Packet {

    private List<Session> sessionList;

    private String groupId;

    @Override
    public Byte supportCommand() {
        return LIST_GROUP_MEMBER_RESP;
    }
}
