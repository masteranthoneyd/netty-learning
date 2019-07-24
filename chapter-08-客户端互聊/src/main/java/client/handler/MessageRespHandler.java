package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.resp.MessageRespPacket;

/**
 * @author ybd
 * @date 19-7-23
 * @contact yangbingdong1994@gmail.com
 */
public class MessageRespHandler extends SimpleChannelInboundHandler<MessageRespPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRespPacket messageRespPacket) throws Exception {
        String fromUserId = messageRespPacket.getFromUserId();
        String fromUserName = messageRespPacket.getFromUserName();
        String message = messageRespPacket.getMessage();
        System.out.println("[" + fromUserId + " | " + fromUserName + "]: " + message);
    }
}
