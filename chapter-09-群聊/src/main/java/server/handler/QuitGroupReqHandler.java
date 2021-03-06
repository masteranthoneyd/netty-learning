package server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.Packet;
import protocol.req.QuitGroupReqPacket;
import protocol.resp.QuitGroupRespPacket;
import session.SessionUtil;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
@ChannelHandler.Sharable
public class QuitGroupReqHandler extends SimpleChannelInboundHandler<QuitGroupReqPacket> {
    public static final SimpleChannelInboundHandler<? extends Packet> INSTANCE = new QuitGroupReqHandler();

    private QuitGroupReqHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupReqPacket reqPacket) {
        String groupId = reqPacket.getGroupId();

        QuitGroupRespPacket quitGroupRespPacket = new QuitGroupRespPacket();
        quitGroupRespPacket.setSuccess(true);
        quitGroupRespPacket.setSession(SessionUtil.getSession(ctx.channel()));
        quitGroupRespPacket.setGroupId(groupId);

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.writeAndFlush(quitGroupRespPacket);
        channelGroup.remove(ctx.channel());
    }
}
