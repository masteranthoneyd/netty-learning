package client.console;

import io.netty.channel.Channel;
import protocol.req.JoinGroupReqPacket;

import java.util.Scanner;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
public class JoinGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入 groupId, 加入聊群:");
        String groupId = scanner.next();
        JoinGroupReqPacket joinGroupReqPacket = new JoinGroupReqPacket();
        joinGroupReqPacket.setGroupId(groupId);
        channel.writeAndFlush(joinGroupReqPacket);
    }
}
