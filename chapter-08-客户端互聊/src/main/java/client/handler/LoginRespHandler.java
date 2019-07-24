package client.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.req.LoginReqPacket;
import protocol.req.MessageReqPacket;
import protocol.resp.LoginRespPacket;

import java.util.Scanner;

import static session.SessionUtil.bindSession;
import static session.SessionUtil.hasLogin;

/**
 * @author ybd
 * @date 19-7-23
 * @contact yangbingdong1994@gmail.com
 */
public class LoginRespHandler extends SimpleChannelInboundHandler<LoginRespPacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        listenSystemIn(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRespPacket loginRespPacket) {
        if (loginRespPacket.isSuccess()) {
            System.out.println("用户 [" + loginRespPacket.getUserName() + "] 登录成功, userId 为: " + loginRespPacket.getUserId());
            bindSession(loginRespPacket.toSession(), ctx.channel());
        } else {
            System.out.println("登录失败");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("链接被关闭了");
        Runtime.getRuntime().exit(0);
        super.channelInactive(ctx);
    }

    private static void listenSystemIn(Channel channel) {
        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            while (true) {
                if (hasLogin(channel)) {
                    MessageReqPacket packet = new MessageReqPacket();
                    packet.setToUserId(sc.next())
                          .setMessage(sc.next());
                    channel.writeAndFlush(packet);
                } else {
                    System.out.println("请输入用户名:");
                    String userName = sc.nextLine();
                    LoginReqPacket packet = new LoginReqPacket();
                    packet.setUsername(userName)
                          .setPassword("123456");
                    channel.writeAndFlush(packet);
                    waitForLoginResponse();
                }
            }
        }).start();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
