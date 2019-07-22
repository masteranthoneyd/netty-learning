package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.LoginRequestPacket;
import protocol.LoginResponsePacket;
import protocol.Packet;
import protocol.PacketCodec;

/**
 * @author ybd
 * @date 19-7-17
 * @contact yangbingdong1994@gmail.com
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.decode(byteBuf);
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            if (loginRequestPacket.getUserId().equals(666)) {
                System.out.println("客户端验证信息通过");
                loginResponsePacket.setSuccess(true)
                                   .setData("成功");
            } else {
                loginResponsePacket.setSuccess(false)
                                   .setData("失败");
            }
        }


        ctx.channel().writeAndFlush(PacketCodec.encode(loginResponsePacket));
    }
}
