package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.req.JoinGroupReqPacket;
import protocol.resp.JoinGroupRespPacket;

import static session.SessionUtil.getChannelGroup;
import static session.SessionUtil.getSession;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
public class JoinGroupReqPacketHandler extends SimpleChannelInboundHandler<JoinGroupReqPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupReqPacket reqPacket) {
        String groupId = reqPacket.getGroupId();
        ChannelGroup channelGroup = getChannelGroup(groupId);
        Channel channel = ctx.channel();
        channelGroup.add(channel);

        JoinGroupRespPacket joinGroupRespPacket = new JoinGroupRespPacket();
        joinGroupRespPacket.setSuccess(true)
                           .setGroupId(groupId)
                           .setSession(getSession(channel));
        channelGroup.writeAndFlush(joinGroupRespPacket);
    }
}
