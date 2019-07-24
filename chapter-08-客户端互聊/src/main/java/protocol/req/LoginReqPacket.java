package protocol.req;

import lombok.Data;
import lombok.experimental.Accessors;
import protocol.Packet;

import static protocol.Command.LOGIN_REQ;

/**
 * @author ybd
 * @date 19-7-17
 * @contact yangbingdong1994@gmail.com
 */
@Data
@Accessors(chain = true)
public class LoginReqPacket implements Packet {

    private String username;

    private String password;

    @Override
    public Byte supportCommand() {
        return LOGIN_REQ;
    }
}
