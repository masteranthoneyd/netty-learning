package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.req.ListGroupMemberReqPacket;
import protocol.resp.ListGroupMemberRespPacket;
import session.Session;
import session.SessionUtil;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
public class ListGroupMemberReqHandler extends SimpleChannelInboundHandler<ListGroupMemberReqPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMemberReqPacket reqPacket) throws Exception {
        String groupId = reqPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        List<Session> sessionList = channelGroup.stream()
                                                .map(SessionUtil::getSession)
                                                .collect(toList());
        ListGroupMemberRespPacket listGroupMemberRespPacket = new ListGroupMemberRespPacket();
        listGroupMemberRespPacket.setSessionList(sessionList);
        listGroupMemberRespPacket.setGroupId(groupId);
        ctx.channel().writeAndFlush(listGroupMemberRespPacket);
    }
}
