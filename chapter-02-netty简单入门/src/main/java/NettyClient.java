import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author ybd
 * @date 19-7-16
 * @contact yangbingdong1994@gmail.com
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                 .channel(NioSocketChannel.class)
                 .handler(new ChannelInitializer<Channel>() {
                     @Override
                     protected void initChannel(Channel ch) {
                         ch.pipeline().addLast(new StringEncoder());
                     }
                 });

        Channel channel = bootstrap.connect(NettyServer.HOST, NettyServer.PORT).channel();

        while (true) {
            channel.writeAndFlush(LocalDateTime.now() + ": Hello World");
            TimeUnit.SECONDS.sleep(2L);
        }
    }
}
