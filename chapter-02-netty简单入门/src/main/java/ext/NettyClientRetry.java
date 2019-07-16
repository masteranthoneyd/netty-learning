package ext;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author ybd
 * @date 19-7-16
 * @contact yangbingdong1994@gmail.com
 */
public class NettyClientRetry {
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                 .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                 .option(ChannelOption.SO_KEEPALIVE, true)
                 .option(ChannelOption.TCP_NODELAY, true)
                 .channel(NioSocketChannel.class)
                 .handler(new ChannelInitializer<SocketChannel>() {
                     @Override
                     protected void initChannel(SocketChannel ch) throws Exception {

                     }
                 });

        connect(bootstrap, "juejin.im", 8080, 5);

    }

    private static void connect(Bootstrap bootstrap, String inetHost, int inetPort, int retry) {
        bootstrap.connect(inetHost, inetPort).addListener(future -> {

            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接!");
            } else {
                int order = MAX_RETRY - retry + 1;
                int delay = 1 << order;
                System.err.println(LocalDateTime.now() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config()
                         .group()
                         .schedule(() -> connect(bootstrap, inetHost, inetPort, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }
}
