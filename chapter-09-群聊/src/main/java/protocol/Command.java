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

    Byte LOGOUT_REQ = Byte.valueOf("5");
    Byte LOGOUT_RESP = Byte.valueOf("6");


    Byte CREATE_GROUP_REQ = Byte.valueOf("7");
    Byte CREATE_GROUP_RESP = Byte.valueOf("8");

    Byte JOIN_GROUP_REQ = Byte.valueOf("9");
    Byte JOIN_GROUP_RESP = Byte.valueOf("10");

    Byte QUIT_GROUP_REQ = Byte.valueOf("11");
    Byte QUIT_GROUP_RESP = Byte.valueOf("12");

    Byte LIST_GROUP_MEMBER_REQ = Byte.valueOf("13");
    Byte LIST_GROUP_MEMBER_RESP = Byte.valueOf("14");

}
