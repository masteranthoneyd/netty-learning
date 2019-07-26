package server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.Packet;
import protocol.req.GroupMessageReqPacket;
import protocol.resp.GroupMessageRespPacket;
import session.SessionUtil;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
@ChannelHandler.Sharable
public class GroupMessageReqHandler extends SimpleChannelInboundHandler<GroupMessageReqPacket> {

    public static final SimpleChannelInboundHandler<? extends Packet> INSTANCE = new GroupMessageReqHandler();

    private GroupMessageReqHandler() {
    }

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
