package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import protocol.req.CreateGroupReqPacket;
import protocol.resp.CreateGroupRespPacket;
import session.SessionUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static session.SessionUtil.randomUserId;

/**
 * @author ybd
 * @date 19-7-25
 * @contact yangbingdong1994@gmail.com
 */
public class CreateGroupReqPacketHandler extends SimpleChannelInboundHandler<CreateGroupReqPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupReqPacket reqPacket) {
        System.out.println("收到创建群请求: " + reqPacket);
        List<String> userIdList = reqPacket.getUserIdList();

        Set<String> userNameList = new HashSet<>(16);
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }
        Channel channel = ctx.channel();
        channelGroup.add(channel);
        userNameList.add(SessionUtil.getSession(channel).getUserName());

        CreateGroupRespPacket createGroupRespPacket = new CreateGroupRespPacket();
        createGroupRespPacket.setGroupId(randomUserId())
                             .setSuccess(true)
                             .setUserNameSet(userNameList);

        channelGroup.writeAndFlush(createGroupRespPacket);
        System.out.print("群创建成功，id 为[" + createGroupRespPacket.getGroupId() + "], ");
        System.out.println("群里面有：" + Arrays.toString(createGroupRespPacket.getUserNameSet().toArray()));

        SessionUtil.bindChannelGroup(createGroupRespPacket.getGroupId(), channelGroup);
    }
}
