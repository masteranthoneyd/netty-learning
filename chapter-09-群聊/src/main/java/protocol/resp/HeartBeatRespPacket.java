package protocol.resp;

import protocol.Packet;

import static protocol.Command.HEART_BEAT_RESP;

/**
 * @author ybd
 * @date 19-7-30
 * @contact yangbingdong1994@gmail.com
 */
public class HeartBeatRespPacket implements Packet {
    @Override
    public Byte supportCommand() {
        return HEART_BEAT_RESP;
    }
}
