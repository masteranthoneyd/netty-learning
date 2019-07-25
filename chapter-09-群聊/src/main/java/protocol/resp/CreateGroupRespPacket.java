package protocol.resp;

import lombok.Data;
import lombok.experimental.Accessors;
import protocol.Command;
import protocol.Packet;

import java.util.Set;

/**
 * @author ybd
 * @date 19-7-25
 * @contact yangbingdong1994@gmail.com
 */
@Data
@Accessors(chain = true)
public class CreateGroupRespPacket implements Packet {

    private Set<String> userNameSet;

    private String groupId;

    private boolean success;

    @Override
    public Byte supportCommand() {
        return Command.CREATE_GROUP_RESP;
    }
}
