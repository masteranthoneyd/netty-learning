package client;

import client.handler.CreateGroupRespHandler;
import client.handler.GroupMessageRespHandler;
import client.handler.HeartBeatTimerHandler;
import client.handler.JoinGroupRespHandler;
import client.handler.ListGroupMemberRespHandler;
import client.handler.LoginRespHandler;
import client.handler.LogoutRespHandler;
import client.handler.MessageRespHandler;
import client.handler.QuitGroupRespHandler;
import codec.PacketDecoder;
import codec.PacketEncoder;
import codec.Spliter;
import handler.IMIdleStateHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import server.NettyServer;

/**
 * @author ybd
 * @date 19-7-16
 * @contact yangbingdong1994@gmail.com
 */
public class NettyClient {
    private static NioEventLoopGroup group = new NioEventLoopGroup();
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                 .channel(NioSocketChannel.class)
                 .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                 .option(ChannelOption.SO_KEEPALIVE, true)
                 .option(ChannelOption.TCP_NODELAY, true)
                 .handler(new ChannelInitializer<Channel>() {
                     @Override
                     protected void initChannel(Channel ch) {
                         ch.pipeline()
                           .addLast(new IMIdleStateHandler())
                           .addLast(new Spliter())
                           .addLast(new PacketDecoder())

                           .addLast(new LoginRespHandler())
                           .addLast(new MessageRespHandler())
                           .addLast(new LogoutRespHandler())
                           .addLast(new CreateGroupRespHandler())
                           .addLast(new JoinGroupRespHandler())
                           .addLast(new QuitGroupRespHandler())
                           .addLast(new ListGroupMemberRespHandler())
                           .addLast(new GroupMessageRespHandler())

                           .addLast(PacketEncoder.INSTANCE)
                           .addLast(new HeartBeatTimerHandler());
                     }
                 })
                 .connect(NettyServer.HOST, NettyServer.PORT)
                 .addListener((ChannelFutureListener) future -> {
                     if (future.isSuccess()) {
                         System.out.println("Connect Success!");
                     } else {
                         System.out.println("Connect Fail!");
                     }
                 });
    }
}
