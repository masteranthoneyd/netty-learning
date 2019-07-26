package client.console;

import io.netty.channel.Channel;
import protocol.req.GroupMessageReqPacket;

import java.util.Scanner;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
public class SendToGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个某个群组：");

        String toGroupId = scanner.next();
        String message = scanner.next();
        channel.writeAndFlush(new GroupMessageReqPacket().setGroupId(toGroupId).setMessage(message));
    }
}
