package client.console;

import io.netty.channel.Channel;
import protocol.req.ListGroupMemberReqPacket;

import java.util.Scanner;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */

public class ListGroupMemberConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入 groupId, 获取群成员列表");

        String groupId = scanner.next();
        ListGroupMemberReqPacket listGroupMemberReqPacket = new ListGroupMemberReqPacket();
        listGroupMemberReqPacket.setGroupId(groupId);
        channel.writeAndFlush(listGroupMemberReqPacket);
    }
}
