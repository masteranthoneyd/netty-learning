package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.req.HeartBeatReqPacket;
import protocol.resp.HeartBeatRespPacket;

/**
 * @author ybd
 * @date 19-7-30
 * @contact yangbingdong1994@gmail.com
 */
public class HeartBeatReqHandler extends SimpleChannelInboundHandler<HeartBeatReqPacket> {

    public static final HeartBeatReqHandler INSTANCE = new HeartBeatReqHandler();

    private HeartBeatReqHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatReqPacket msg) {
        ctx.writeAndFlush(new HeartBeatRespPacket());
    }
}
