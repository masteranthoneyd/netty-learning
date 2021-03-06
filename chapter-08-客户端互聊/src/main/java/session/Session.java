package session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ybd
 * @date 19-7-24
 * @contact yangbingdong1994@gmail.com
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Session {

    private String userId;

    private String userName;

    public static Session of(String userId, String userName) {
        return new Session(userId, userName);
    }
}
