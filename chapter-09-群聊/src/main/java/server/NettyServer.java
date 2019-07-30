package server;

import codec.PacketCodecHandler;
import codec.Spliter;
import handler.IMIdleStateHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import server.handler.AuthHandler;
import server.handler.HeartBeatReqHandler;
import server.handler.IMHandler;
import server.handler.LoginReqHandler;

/**
 * @author ybd
 * @date 19-7-16
 * @contact yangbingdong1994@gmail.com
 */
public class NettyServer {

    public static final int PORT = 7000;
    public static final String HOST = "192.168.6.113";

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        serverBootstrap.group(boss, worker)
                       .channel(NioServerSocketChannel.class)
                       .option(ChannelOption.SO_BACKLOG, 1024)
                       .childOption(ChannelOption.SO_KEEPALIVE, true)
                       .childOption(ChannelOption.TCP_NODELAY, true)
                       .handler(new LoggingHandler())
                       .childHandler(new ChannelInitializer<NioSocketChannel>() {
                           @Override
                           protected void initChannel(NioSocketChannel ch) {
                               ch.pipeline()
                                 .addLast(new IMIdleStateHandler())
                                 .addLast(new Spliter())
                                 .addLast(PacketCodecHandler.INSTANCE)

                                 .addLast(LoginReqHandler.INSTANCE)
                                 .addLast(HeartBeatReqHandler.INSTANCE)
                                 .addLast(AuthHandler.INSTANCE)

                                 .addLast(IMHandler.INSTANCE);


                           }
                       })
                       .bind(HOST, PORT)
                       .addListener((ChannelFutureListener) future -> {
                           if (future.isSuccess()) {
                               System.out.println("绑定[" + PORT + "]端口成功!");
                           }
                       });
    }
}
