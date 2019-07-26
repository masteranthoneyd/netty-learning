package server;

import codec.PacketDecoder;
import codec.PacketEncoder;
import codec.Spliter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import server.handler.AuthHandler;
import server.handler.CreateGroupReqPacketHandler;
import server.handler.JoinGroupReqPacketHandler;
import server.handler.ListGroupMemberReqHandler;
import server.handler.LoginReqHandler;
import server.handler.LogoutReqHandler;
import server.handler.MessageReqHandler;
import server.handler.QuitGroupReqHandler;

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
                                 .addLast(new Spliter())
                                 .addLast(new PacketDecoder())

                                 .addLast(new LoginReqHandler())
                                 .addLast(new AuthHandler())
                                 .addLast(new LogoutReqHandler())
                                 .addLast(new CreateGroupReqPacketHandler())
                                 .addLast(new MessageReqHandler())
                                 .addLast(new JoinGroupReqPacketHandler())
                                 .addLast(new QuitGroupReqHandler())
                                 .addLast(new ListGroupMemberReqHandler())

                                 .addLast(new PacketEncoder());

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
