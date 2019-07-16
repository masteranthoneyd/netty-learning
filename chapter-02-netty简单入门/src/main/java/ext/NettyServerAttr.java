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
import io.netty.util.AttributeKey;

/**
 * @author ybd
 * @date 19-7-16
 * @contact yangbingdong1994@gmail.com
 */
public class NettyServerAttr {

    static final int PORT = 7000;
    static final String HOST = "0.0.0.0";

    /**
     * attr()方 法可以给服务端的 channel，也就是NioServerSocketChannel指定一些自定义属性，然后我们可以通过channel.attr()取出这个属性
     * childAttr() 可以给每一条连接指定自定义属性
     */
    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        AttributeKey<String> appName = AttributeKey.newInstance("appName");
        AttributeKey<String> appConnectName = AttributeKey.newInstance("appConnectName");

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
                       })
                       .attr(appName, "nettyServer")
                       .childAttr(appConnectName, "appConnectName")
                       .bind(HOST, PORT)
                       .addListener((ChannelFutureListener) future -> {
                           System.out.println(future.channel().attr(appName));
                           if (future.isSuccess()) {
                               System.out.println("端口绑定成功");
                           } else {
                               System.out.println("端口绑定失败");
                           }
                       });

    }
}
