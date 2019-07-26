package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.resp.ListGroupMemberRespPacket;

import java.util.stream.Collectors;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
public class ListGroupMemberRespHandler extends SimpleChannelInboundHandler<ListGroupMemberRespPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMemberRespPacket respPacket) {
        String members = respPacket.getSessionList()
                                   .stream()
                                   .map(e -> e.getUserId() + ":" + e.getUserName())
                                   .collect(Collectors.joining(","));
        System.out.println("群[" + respPacket.getGroupId() + "]中的人包括：[" + members + "]");
    }
}
