package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.resp.GroupMessageRespPacket;
import session.Session;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
public class GroupMessageRespHandler extends SimpleChannelInboundHandler<GroupMessageRespPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRespPacket respPacket) {
        String fromGroupId = respPacket.getFromGroupId();
        Session fromUser = respPacket.getFromUser();
        System.out.println("收到群[" + fromGroupId + "]中[" + fromUser + "]发来的消息：" + respPacket.getMessage());


    }
}
