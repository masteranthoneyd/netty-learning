package client.console;

import io.netty.channel.Channel;
import protocol.req.MessageReqPacket;

import java.util.Scanner;

/**
 * @author ybd
 * @date 19-7-25
 * @contact yangbingdong1994@gmail.com
 */
public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个某个用户(用户id与内容使用间隔符分开):");
        MessageReqPacket packet = new MessageReqPacket();
        packet.setToUserId(scanner.next())
              .setMessage(scanner.next());
        channel.writeAndFlush(packet);
    }
}
