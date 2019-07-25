package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.resp.LogoutRespPacket;
import session.SessionUtil;

/**
 * @author ybd
 * @date 19-7-25
 * @contact yangbingdong1994@gmail.com
 */
public class LogoutRespHandler extends SimpleChannelInboundHandler<LogoutRespPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRespPacket logoutRespPacket) throws Exception {
        if (logoutRespPacket.isSuccess()) {
            System.out.println("退出成功");
            SessionUtil.unBindSession(ctx.channel());
        }

    }
}
