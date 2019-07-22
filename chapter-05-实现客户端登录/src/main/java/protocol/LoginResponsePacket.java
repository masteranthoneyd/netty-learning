package protocol;

import lombok.Data;
import lombok.experimental.Accessors;

import static protocol.Command.LOGIN_RESPONSE;

/**
 * @author ybd
 * @date 19-7-17
 * @contact yangbingdong1994@gmail.com
 */
@Data
@Accessors(chain = true)
public class LoginResponsePacket implements Packet {

    private boolean success;

    private String data;

    @Override
    public Byte supportCommand() {
        return LOGIN_RESPONSE;
    }
}
