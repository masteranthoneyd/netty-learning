package server.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.Packet;

import java.util.HashMap;
import java.util.Map;

import static protocol.Command.CREATE_GROUP_REQ;
import static protocol.Command.GROUP_MESSAGE_REQ;
import static protocol.Command.JOIN_GROUP_REQ;
import static protocol.Command.LIST_GROUP_MEMBER_REQ;
import static protocol.Command.LOGOUT_REQ;
import static protocol.Command.MESSAGE_REQ;
import static protocol.Command.QUIT_GROUP_REQ;

/**
 * @author ybd
 * @date 19-7-26
 * @contact yangbingdong1994@gmail.com
 */
@Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {
    public static final IMHandler INSTANCE = new IMHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler() {
        handlerMap = new HashMap<>(16);

        handlerMap.put(MESSAGE_REQ, MessageReqHandler.INSTANCE);
        handlerMap.put(CREATE_GROUP_REQ, CreateGroupReqHandler.INSTANCE);
        handlerMap.put(JOIN_GROUP_REQ, JoinGroupReqHandler.INSTANCE);
        handlerMap.put(QUIT_GROUP_REQ, QuitGroupReqHandler.INSTANCE);
        handlerMap.put(LIST_GROUP_MEMBER_REQ, ListGroupMemberReqHandler.INSTANCE);
        handlerMap.put(GROUP_MESSAGE_REQ, GroupMessageReqHandler.INSTANCE);
        handlerMap.put(LOGOUT_REQ, LogoutReqHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlerMap.get(packet.supportCommand()).channelRead(ctx, packet);
    }
}
