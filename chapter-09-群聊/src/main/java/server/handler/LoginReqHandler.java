package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.req.LoginReqPacket;
import protocol.resp.LoginRespPacket;

import java.time.LocalDateTime;

import static session.SessionUtil.bindSession;
import static session.SessionUtil.randomUserId;

/**
 * @author ybd
 * @date 19-7-23
 * @contact yangbingdong1994@gmail.com
 */
public class LoginReqHandler extends SimpleChannelInboundHandler<LoginReqPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginReqPacket loginReqPacket) throws Exception {
        System.out.println(LocalDateTime.now() + ": 接受到客户端登录请求 " + loginReqPacket);
        Channel channel = ctx.channel();
        LoginRespPacket loginRespPacket = new LoginRespPacket();
        if (valid(loginRespPacket)) {
            String userId = randomUserId();
            System.out.println("[" + loginReqPacket.getUsername() + "] 登录成功");
            loginRespPacket.setSuccess(true)
                           .setUserName(loginReqPacket.getUsername())
                           .setUserId(userId)
                           .setData("成功");
            bindSession(loginRespPacket.toSession(), channel);
        } else {
            loginRespPacket.setSuccess(false)
                           .setData("失败");
        }
        channel.writeAndFlush(loginRespPacket);
    }

    private boolean valid(LoginRespPacket loginRespPacket) {
        return true;
    }
}
