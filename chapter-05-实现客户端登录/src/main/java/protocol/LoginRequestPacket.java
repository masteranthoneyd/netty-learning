package protocol;

import lombok.Data;
import lombok.experimental.Accessors;

import static protocol.Command.LOGIN_REQUEST;

/**
 * @author ybd
 * @date 19-7-17
 * @contact yangbingdong1994@gmail.com
 */
@Data
@Accessors(chain = true)
public class LoginRequestPacket implements Packet {

    private Integer userId;

    private String username;

    private String password;

    @Override
    public Byte supportCommand() {
        return LOGIN_REQUEST;
    }
}
