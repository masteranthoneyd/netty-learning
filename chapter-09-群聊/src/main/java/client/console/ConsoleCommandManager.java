package client.console;

import io.netty.channel.Channel;
import session.SessionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author ybd
 * @date 19-7-25
 * @contact yangbingdong1994@gmail.com
 */
public class ConsoleCommandManager implements ConsoleCommand {

    private Map<String, ConsoleCommand> consoleCommandMap;

    {
        consoleCommandMap = new HashMap<>(16);
        consoleCommandMap.putIfAbsent("createGroup", new CreateGroupConsoleCommand());
        consoleCommandMap.putIfAbsent("ss", new SendToUserConsoleCommand());
        consoleCommandMap.putIfAbsent("logout", new LogoutConsoleCommand());
        consoleCommandMap.putIfAbsent("joinGroup", new JoinGroupConsoleCommand());
        consoleCommandMap.putIfAbsent("quitGroup", new QuitGroupConsoleCommand());
    }


    @Override
    public void exec(Scanner scanner, Channel channel) {

        if (!SessionUtil.hasLogin(channel)) {
            System.err.println("请重新登录!");
            return;
        }

        String command = scanner.next();
        ConsoleCommand consoleCommand = consoleCommandMap.get(command);
        if (consoleCommand == null) {
            System.err.println("无法识别 [" + command + "] 指令, 请重新输入");
            return;
        }
        consoleCommand.exec(scanner, channel);
    }
}
