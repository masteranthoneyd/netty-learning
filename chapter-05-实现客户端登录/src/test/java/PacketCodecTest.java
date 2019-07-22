import io.netty.buffer.ByteBuf;
import org.junit.Assert;
import org.junit.Test;
import protocol.LoginRequestPacket;
import protocol.Packet;
import protocol.PacketCodec;

/**
 * @author ybd
 * @date 19-7-18
 * @contact yangbingdong1994@gmail.com
 */
public class PacketCodecTest {

    @Test
    public void encodeAndDecode() {
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(666)
              .setPassword("666")
              .setUsername("yangbingdong");

        ByteBuf encode = PacketCodec.encode(packet);
        Packet decode = PacketCodec.decode(encode);

        Assert.assertEquals(packet, decode);
    }
}
