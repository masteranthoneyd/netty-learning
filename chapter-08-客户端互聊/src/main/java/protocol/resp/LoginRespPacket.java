package protocol.resp;

import lombok.Data;
import lombok.experimental.Accessors;
import protocol.Packet;
import session.Session;

import static protocol.Command.LOGIN_RESP;

/**
 * @author ybd
 * @date 19-7-17
 * @contact yangbingdong1994@gmail.com
 */
@Data
@Accessors(chain = true)
public class LoginRespPacket implements Packet {

    private boolean success;

    private String userId;

    private String userName;

    private String data;

    @Override
    public Byte supportCommand() {
        return LOGIN_RESP;
    }

    public Session toSession() {
        return Session.of(userId, userName);
    }
}
