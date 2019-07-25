package client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author ybd
 * @date 19-7-25
 * @contact yangbingdong1994@gmail.com
 */
public interface ConsoleCommand {

    void exec(Scanner scanner, Channel channel);

}
