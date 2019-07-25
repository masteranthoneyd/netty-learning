package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.req.LogoutReqPacket;
import protocol.resp.LogoutRespPacket;
import session.SessionUtil;

/**
 * @author ybd
 * @date 19-7-25
 * @contact yangbingdong1994@gmail.com
 */
public class LogoutReqHandler extends SimpleChannelInboundHandler<LogoutReqPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutReqPacket msg) throws Exception {
        Channel channel = ctx.channel();
        SessionUtil.unBindSession(channel);
        LogoutRespPacket logoutRespPacket = new LogoutRespPacket();
        logoutRespPacket.setSuccess(true);
        channel.writeAndFlush(logoutRespPacket);
    }
}
