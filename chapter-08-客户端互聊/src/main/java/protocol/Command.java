package protocol;

/**
 * @author ybd
 * @date 19-7-17
 * @contact yangbingdong1994@gmail.com
 */
public interface Command {
    Byte LOGIN_REQ = Byte.valueOf("1");
    Byte LOGIN_RESP = Byte.valueOf("2");

    Byte MESSAGE_REQ = Byte.valueOf("3");
    Byte MESSAGE_RESP = Byte.valueOf("4");


}
