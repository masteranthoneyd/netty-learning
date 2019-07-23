package protocol;

/**
 * @author ybd
 * @date 19-7-17
 * @contact yangbingdong1994@gmail.com
 */
public interface Packet {

    Byte PROTOCOL_VERSION = Byte.valueOf("1");

    Byte supportCommand();
}
