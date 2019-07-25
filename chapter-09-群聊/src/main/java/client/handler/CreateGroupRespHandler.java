package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.resp.CreateGroupRespPacket;

/**
 * @author ybd
 * @date 19-7-25
 * @contact yangbingdong1994@gmail.com
 */
public class CreateGroupRespHandler extends SimpleChannelInboundHandler<CreateGroupRespPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRespPacket respPacket) {
        System.out.print("群创建成功，id 为[" + respPacket.getGroupId() + "], ");
        System.out.println("群里面有：" + respPacket.getUserNameSet());
    }
}
