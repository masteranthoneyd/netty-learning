package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.resp.QuitGroupRespPacket;
import session.Session;

import static session.SessionUtil.getSession;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
public class QuitGroupRespHandler extends SimpleChannelInboundHandler<QuitGroupRespPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRespPacket respPacket) {
        Session session = respPacket.getSession();
        if (session.getUserId().equals(getSession(ctx.channel()).getUserId())) {
            System.out.println("退出聊群" + respPacket.getGroupId() + "成功");
        } else {
            System.out.println("[" + session.getUserName() + "] 退出了聊群" + respPacket.getGroupId());
        }
    }
}
