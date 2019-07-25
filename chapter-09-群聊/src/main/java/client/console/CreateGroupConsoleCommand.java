package client.console;

import io.netty.channel.Channel;
import protocol.req.CreateGroupReqPacket;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author ybd
 * @date 19-7-25
 * @contact yangbingdong1994@gmail.com
 */
public class CreateGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("【拉人群聊】输入 userId 列表，userId 之间英文逗号隔开: ");
        String userIds = scanner.next();
        CreateGroupReqPacket createGroupReqPacket = new CreateGroupReqPacket();
        createGroupReqPacket.setUserIdList(Arrays.asList(userIds.split(",")));
        channel.writeAndFlush(createGroupReqPacket);
    }
}
