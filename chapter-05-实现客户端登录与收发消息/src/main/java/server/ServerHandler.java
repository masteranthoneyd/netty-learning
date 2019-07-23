package server;

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

import java.util.Date;

import static util.LoginUtil.hasLogin;
import static util.LoginUtil.markAsLogin;

/**
 * @author ybd
 * @date 19-7-17
 * @contact yangbingdong1994@gmail.com
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Channel channel = ctx.channel();
        Packet packet = PacketCodec.decode(byteBuf);
        if (packet instanceof LoginReqPacket) {
            LoginRespPacket loginRespPacket = new LoginRespPacket();
            LoginReqPacket loginReqPacket = (LoginReqPacket) packet;
            if (loginReqPacket.getUserId().equals(666)) {
                System.out.println("客户端验证信息通过");
                loginRespPacket.setSuccess(true)
                               .setData("成功");
                markAsLogin(channel);
            } else {
                loginRespPacket.setSuccess(false)
                               .setData("失败");
            }
            channel.writeAndFlush(PacketCodec.encode(loginRespPacket, channel.alloc()));
        } else if (packet instanceof MessageReqPacket) {
            if (hasLogin(channel)) {
                MessageReqPacket messageRequestPacket = ((MessageReqPacket) packet);
                MessageRespPacket messageResponsePacket = new MessageRespPacket();
                System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());
                messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
                channel.writeAndFlush(PacketCodec.encode(messageResponsePacket, channel.alloc()));
            }
        }


    }
}
