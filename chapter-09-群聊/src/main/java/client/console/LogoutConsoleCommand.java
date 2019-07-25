package client.console;

import io.netty.channel.Channel;
import protocol.req.LogoutReqPacket;

import java.util.Scanner;

/**
 * @author ybd
 * @date 19-7-25
 * @contact yangbingdong1994@gmail.com
 */
public class LogoutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutReqPacket logoutReqPacket = new LogoutReqPacket();
        channel.writeAndFlush(logoutReqPacket);
    }
}
