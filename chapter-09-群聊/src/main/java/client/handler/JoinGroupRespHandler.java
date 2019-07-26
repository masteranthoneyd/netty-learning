package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.resp.JoinGroupRespPacket;
import session.Session;

import static session.SessionUtil.getSession;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
public class JoinGroupRespHandler extends SimpleChannelInboundHandler<JoinGroupRespPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRespPacket respPacket) {
        Session session = respPacket.getSession();
        String groupId = respPacket.getGroupId();
        if (getSession(ctx.channel()).getUserId().equals(session.getUserId())) {
            System.out.println("加入群[" + groupId + "]成功!");
        } else {
            System.out.println("[" + session.getUserName() + "] 加入了聊群" + groupId);
        }
    }
}
