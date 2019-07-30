package client.handler;

import client.console.ConsoleCommandManager;
import client.console.LoginConsoleCommand;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        listenSystemIn(ctx.channel());
        super.channelActive(ctx);
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
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            while (true) {
                if (hasLogin(channel)) {
                    consoleCommandManager.exec(sc, channel);
                } else {
                    loginConsoleCommand.exec(sc, channel);
                }
            }
        }).start();
    }


}
