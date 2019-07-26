package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.req.GroupMessageReqPacket;
import protocol.resp.GroupMessageRespPacket;
import session.SessionUtil;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
public class GroupMessageReqHandler extends SimpleChannelInboundHandler<GroupMessageReqPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageReqPacket reqPacket) {
        String groupId = reqPacket.getGroupId();
        String message = reqPacket.getMessage();

        GroupMessageRespPacket groupMessageRespPacket = new GroupMessageRespPacket();
        groupMessageRespPacket.setFromGroupId(groupId)
                              .setMessage(message)
                              .setFromUser(SessionUtil.getSession(ctx.channel()));

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.writeAndFlush(groupMessageRespPacket);

    }
}
