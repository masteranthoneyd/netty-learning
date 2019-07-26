package codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import protocol.Packet;
import protocol.PacketCodec;

import java.util.List;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
@Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {

    public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();

    private PacketCodecHandler() {

    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = ctx.alloc().ioBuffer();
        PacketCodec.encode(msg, byteBuf);
        out.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        Packet decode = PacketCodec.decode(msg);
        out.add(decode);
    }
}
