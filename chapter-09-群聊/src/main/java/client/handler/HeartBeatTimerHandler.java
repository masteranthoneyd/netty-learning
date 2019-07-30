package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.req.HeartBeatReqPacket;

import java.util.concurrent.TimeUnit;

/**
 * @author ybd
 * @date 19-7-30
 * @contact yangbingdong1994@gmail.com
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);

        super.channelActive(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(new HeartBeatReqPacket());
                scheduleSendHeartBeat(ctx);
            }
        }, 5, TimeUnit.SECONDS);
    }

}
