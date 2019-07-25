package protocol.req;

import lombok.Data;
import protocol.Command;
import protocol.Packet;

import java.util.List;

/**
 * @author ybd
 * @date 19-7-25
 * @contact yangbingdong1994@gmail.com
 */
@Data
public class CreateGroupReqPacket implements Packet {

    private List<String> userIdList;

    @Override
    public Byte supportCommand() {
        return Command.CREATE_GROUP_REQ;
    }
}
