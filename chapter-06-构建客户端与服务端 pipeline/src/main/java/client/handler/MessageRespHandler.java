package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.resp.MessageRespPacket;

import java.time.LocalDateTime;

/**
 * @author ybd
 * @date 19-7-23
 * @contact yangbingdong1994@gmail.com
 */
public class MessageRespHandler extends SimpleChannelInboundHandler<MessageRespPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRespPacket messageRespPacket) throws Exception {
        System.out.println(LocalDateTime.now() + ": 收到服务端的消息: " + messageRespPacket.getMessage());
    }
}
