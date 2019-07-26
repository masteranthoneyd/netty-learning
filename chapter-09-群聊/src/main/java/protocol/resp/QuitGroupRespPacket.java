package protocol.resp;

import lombok.Data;
import protocol.Packet;
import session.Session;

import static protocol.Command.QUIT_GROUP_RESP;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
@Data
public class QuitGroupRespPacket implements Packet {
    private boolean success;

    private Session session;

    private String groupId;

    @Override
    public Byte supportCommand() {
        return QUIT_GROUP_RESP;
    }
}
