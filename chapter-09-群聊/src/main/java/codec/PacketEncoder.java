package codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import protocol.Packet;
import protocol.PacketCodec;

/**
 * @author ybd
 * @date 19-7-23
 * @contact yangbingdong1994@gmail.com
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
        PacketCodec.encode(packet, out);
    }
}
