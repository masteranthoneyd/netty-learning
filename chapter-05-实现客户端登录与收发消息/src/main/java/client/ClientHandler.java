package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.Packet;
import protocol.PacketCodec;
import protocol.req.LoginReqPacket;
import protocol.req.MessageReqPacket;
import protocol.resp.LoginRespPacket;
import protocol.resp.MessageRespPacket;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

import static util.LoginUtil.hasLogin;
import static util.LoginUtil.markAsLogin;

/**
 * @author ybd
 * @date 19-7-17
 * @contact yangbingdong1994@gmail.com
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        // 构建登录对象
        LoginReqPacket packet = new LoginReqPacket();
        packet.setUsername("ybd")
              .setPassword("123456")
              .setUserId(666);

        // 编码
        Channel channel = ctx.channel();
        ByteBuf byteBuf = PacketCodec.encode(packet, channel.alloc());
        channel.writeAndFlush(byteBuf);
        listenSystemIn(channel);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.decode(byteBuf);
        System.out.println(new Date() + ": 客户端读到数据 -> " + packet);

        if (packet instanceof LoginRespPacket) {
            LoginRespPacket responsePacket = (LoginRespPacket) packet;
            if (responsePacket.isSuccess()) {
                System.out.println("登录成功");
                markAsLogin(ctx.channel());
            } else {
                System.out.println("登录失败");
            }
        } else if (packet instanceof MessageRespPacket) {
            MessageRespPacket messageResponsePacket = (MessageRespPacket) packet;
            System.out.println(LocalDateTime.now() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());
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
                    ByteBuf byteBuf = PacketCodec.encode(packet, channel.alloc());
                    channel.writeAndFlush(byteBuf);
                }
            }
        }).start();
    }
}
