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
public class NettyServerHandler {

    static final int PORT = 7000;
    static final String HOST = "0.0.0.0";

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        serverBootstrap.group(boss, worker)
                       .channel(NioServerSocketChannel.class)
                       /* childHandler()用于指定处理新连接数据的读写处理逻辑, handler()用于指定在服务端启动过程中的一些逻辑 */
                       .handler(new ChannelInitializer<NioServerSocketChannel>() {
                           @Override
                           protected void initChannel(NioServerSocketChannel ch) {
                               System.out.println("Server initializing...");
                           }
                       })
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
