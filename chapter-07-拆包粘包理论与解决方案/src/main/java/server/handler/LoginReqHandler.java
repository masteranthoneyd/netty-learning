package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.req.LoginReqPacket;
import protocol.resp.LoginRespPacket;

import java.time.LocalDateTime;

import static util.LoginUtil.markAsLogin;

/**
 * @author ybd
 * @date 19-7-23
 * @contact yangbingdong1994@gmail.com
 */
public class LoginReqHandler extends SimpleChannelInboundHandler<LoginReqPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginReqPacket loginReqPacket) throws Exception {
        System.out.println(LocalDateTime.now() + ": 接受到客户端登录请求......");
        Channel channel = ctx.channel();
        LoginRespPacket loginRespPacket = new LoginRespPacket();
        if (loginReqPacket.getUserId().equals(666)) {
            System.out.println("客户端验证信息通过");
            loginRespPacket.setSuccess(true)
                           .setData("成功");
            markAsLogin(channel);
        } else {
            loginRespPacket.setSuccess(false)
                           .setData("失败");
        }
        channel.writeAndFlush(loginRespPacket);
    }
}
