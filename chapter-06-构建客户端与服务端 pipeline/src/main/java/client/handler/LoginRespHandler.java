package client.handler;

import client.NettyClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.req.LoginReqPacket;
import protocol.req.MessageReqPacket;
import protocol.resp.LoginRespPacket;

import java.util.Scanner;

import static util.LoginUtil.hasLogin;
import static util.LoginUtil.markAsLogin;

/**
 * @author ybd
 * @date 19-7-23
 * @contact yangbingdong1994@gmail.com
 */
public class LoginRespHandler extends SimpleChannelInboundHandler<LoginRespPacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        // 构建登录对象
        LoginReqPacket packet = new LoginReqPacket();
        packet.setUsername("ybd")
              .setPassword("123456")
              .setUserId(666);

        // 编码
        Channel channel = ctx.channel();
        channel.writeAndFlush(packet);
        listenSystemIn(channel);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRespPacket loginRespPacket) throws Exception {
        if (loginRespPacket.isSuccess()) {
            System.out.println("登录成功");
            markAsLogin(ctx.channel());
        } else {
            System.out.println("登录失败");
        }
    }

    private static void listenSystemIn(Channel channel) {
        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            boolean active = true;
            while (active) {
                if (hasLogin(channel)) {
                    System.out.println("输入消息发送至服务端: ");
                    String line = sc.nextLine();
                    if ("exit".equals(line.trim())) {
                        active = false;
                        NettyClient.group.shutdownGracefully();
                    }
                    MessageReqPacket packet = new MessageReqPacket();
                    packet.setMessage(line);
                    channel.writeAndFlush(packet);
                }
            }
        }).start();
    }
}
