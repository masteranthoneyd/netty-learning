package ext;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
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
public class NettyServerOption {

    static final int PORT = 7000;
    static final String HOST = "0.0.0.0";

    /**
     * childOption()可以给每条连接设置一些TCP底层相关的属性, 比如:
     *   ChannelOption.SO_KEEPALIVE表示是否开启TCP底层心跳机制，true为开启
     *   ChannelOption.TCP_NODELAY表示是否开启Nagle算法，true表示关闭，false表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
     *
     * option()除了给每个连接设置这一系列属性之外，我们还可以给服务端channel设置一些属性，最常见的就是so_backlog
     */
    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        serverBootstrap.group(boss, worker)
                       .option(ChannelOption.SO_BACKLOG, 1024)
                       .childOption(ChannelOption.SO_KEEPALIVE, true)
                       .childOption(ChannelOption.TCP_NODELAY, true)
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
                       })
                       .bind(HOST, PORT)
                       .addListener((ChannelFutureListener) future -> {
                           if (future.isSuccess()) {
                               System.out.println("端口绑定成功");
                           } else {
                               System.out.println("端口绑定失败");
                           }
                       });

    }
}
