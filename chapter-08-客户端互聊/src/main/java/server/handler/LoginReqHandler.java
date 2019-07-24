package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.req.LoginReqPacket;
import protocol.resp.LoginRespPacket;

import java.time.LocalDateTime;
import java.util.UUID;

import static session.SessionUtil.bindSession;

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
            System.out.println("用户 [" + loginReqPacket.getUsername() + "] 验证信息通过");
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

    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    private boolean valid(LoginRespPacket loginRespPacket) {
        return true;
    }
}
