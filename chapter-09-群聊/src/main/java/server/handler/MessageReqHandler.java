package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.req.MessageReqPacket;
import protocol.resp.MessageRespPacket;
import session.Session;
import session.SessionUtil;

/**
 * @author ybd
 * @date 19-7-23
 * @contact yangbingdong1994@gmail.com
 */
@ChannelHandler.Sharable
public class MessageReqHandler extends SimpleChannelInboundHandler<MessageReqPacket> {
    public static final SimpleChannelInboundHandler<MessageReqPacket> INSTANCE = new MessageReqHandler();

    private MessageReqHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageReqPacket messageReqPacket) {
        Session session = SessionUtil.getSession(ctx.channel());

        MessageRespPacket messageRespPacket = new MessageRespPacket();
        messageRespPacket.setFromUserId(session.getUserId())
                         .setMessage(messageReqPacket.getMessage())
                         .setFromUserName(session.getUserName());

        Channel toUserChannel = SessionUtil.getChannel(messageReqPacket.getToUserId());

        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageRespPacket);
        } else {
            System.err.println("用户 [" + messageReqPacket.getToUserId() + "] 已下线, 发送失败");
        }
    }
}
