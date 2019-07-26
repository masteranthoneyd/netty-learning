package client.console;

import io.netty.channel.Channel;
import protocol.req.QuitGroupReqPacket;

import java.util.Scanner;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
public class QuitGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入 groupId, 退出聊群");

        String groupId = scanner.next();
        QuitGroupReqPacket quitGroupReqPacket = new QuitGroupReqPacket();
        quitGroupReqPacket.setGroupId(groupId);
        channel.writeAndFlush(quitGroupReqPacket);
    }
}
