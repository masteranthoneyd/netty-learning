package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.LoginRequestPacket;
import protocol.LoginResponsePacket;
import protocol.Packet;
import protocol.PacketCodec;

import java.util.Date;

/**
 * @author ybd
 * @date 19-7-17
 * @contact yangbingdong1994@gmail.com
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        // 构建登录对象
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUsername("ybd")
              .setPassword("123456")
              .setUserId(666);

        // 编码
        ByteBuf byteBuf = PacketCodec.encode(packet);
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.decode(byteBuf);
        System.out.println(new Date() + ": 客户端读到数据 -> " + packet);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket responsePacket = (LoginResponsePacket) packet;
            System.out.println(responsePacket.isSuccess() ? "登录成功" : "登录失败");
        }

    }
}
