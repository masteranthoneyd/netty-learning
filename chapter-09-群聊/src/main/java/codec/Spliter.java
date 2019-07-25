package codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import protocol.PacketCodec;

/**
 * @author ybd
 * @date 19-7-23
 * @contact yangbingdong1994@gmail.com
 */
public class Spliter extends LengthFieldBasedFrameDecoder {

    public Spliter() {
        super(Integer.MAX_VALUE, 7, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getInt(in.readerIndex()) != PacketCodec.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }

        return super.decode(ctx, in);
    }
}
