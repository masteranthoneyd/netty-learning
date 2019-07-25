package client.console;

import io.netty.channel.Channel;
import protocol.req.LoginReqPacket;

import java.util.Scanner;

/**
 * @author ybd
 * @date 19-7-25
 * @contact yangbingdong1994@gmail.com
 */
public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入用户名:");
        String userName = scanner.nextLine();
        LoginReqPacket packet = new LoginReqPacket();
        packet.setUsername(userName)
              .setPassword("123456");
        channel.writeAndFlush(packet);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {

        }
    }
}
