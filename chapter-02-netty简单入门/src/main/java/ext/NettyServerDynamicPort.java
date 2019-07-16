package ext;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author ybd
 * @date 19-7-16
 * @contact yangbingdong1994@gmail.com
 */
public class NettyServerDynamicPort {
    static final int PORT = 7000;
    static final String HOST = "0.0.0.0";

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        serverBootstrap.group(boss, worker)
                       .channel(NioServerSocketChannel.class)
                       .childHandler(new ChannelInitializer<NioSocketChannel>() {
                           @Override
                           protected void initChannel(NioSocketChannel ch) {
                               ch.pipeline().addLast(new StringDecoder());
                               ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                                   @Override
                                   protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                       System.out.println(msg);
                                   }
                               });
                           }
                       });
        bind(serverBootstrap, 1000);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port)
                       .addListener((ChannelFutureListener) future -> {
                           if (future.isSuccess()) {
                               System.out.println("端口 [" + port + "] 绑定成功");
                           } else {
                               System.out.println("端口 [" + port + "] 绑定失败");
                               bind(serverBootstrap, port + 1);
                           }
                       });
    }
}
