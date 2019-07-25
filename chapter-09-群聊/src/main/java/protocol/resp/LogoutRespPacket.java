package protocol.resp;

import lombok.Data;
import protocol.Command;
import protocol.Packet;

/**
 * @author ybd
 * @date 19-7-25
 * @contact yangbingdong1994@gmail.com
 */
@Data
public class LogoutRespPacket implements Packet {
    private boolean success;

    @Override
    public Byte supportCommand() {
        return Command.LOGOUT_RESP;
    }
}
