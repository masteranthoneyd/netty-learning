package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.PacketCodec;
import protocol.req.MessageReqPacket;
import protocol.resp.MessageRespPacket;

import java.util.Date;

import static util.LoginUtil.hasLogin;

/**
 * @author ybd
 * @date 19-7-23
 * @contact yangbingdong1994@gmail.com
 */
public class MessageReqHandler extends SimpleChannelInboundHandler<MessageReqPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageReqPacket messageReqPacket) throws Exception {
        Channel channel = ctx.channel();
        if (hasLogin(channel)) {
            MessageRespPacket messageResponsePacket = new MessageRespPacket();
            System.out.println(new Date() + ": 收到客户端消息: " + messageReqPacket.getMessage());
            messageResponsePacket.setMessage("服务端回复【" + messageReqPacket.getMessage() + "】");
            channel.writeAndFlush(PacketCodec.encode(messageResponsePacket, channel.alloc().ioBuffer()));
        }
    }
}
