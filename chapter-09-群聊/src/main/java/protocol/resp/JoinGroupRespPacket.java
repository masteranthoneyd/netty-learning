package protocol.resp;

import lombok.Data;
import lombok.experimental.Accessors;
import protocol.Command;
import protocol.Packet;
import session.Session;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
@Data
@Accessors(chain = true)
public class JoinGroupRespPacket implements Packet {
    private boolean success;

    private String groupId;

    private Session session;

    @Override
    public Byte supportCommand() {
        return Command.JOIN_GROUP_RESP;
    }
}
