package server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * @author ybd
 * @date 19-7-17
 * @contact yangbingdong1994@gmail.com
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(LocalDateTime.now() + ": 服务端读取到数据 -> " + byteBuf.toString(Charset.forName("UTF-8")));
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes("已收到消息!".getBytes(Charset.forName("UTF-8")));
        ctx.channel().writeAndFlush(buffer);
    }
}
