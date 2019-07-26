package protocol;

import io.netty.buffer.ByteBuf;
import protocol.req.CreateGroupReqPacket;
import protocol.req.JoinGroupReqPacket;
import protocol.req.ListGroupMemberReqPacket;
import protocol.req.LoginReqPacket;
import protocol.req.LogoutReqPacket;
import protocol.req.MessageReqPacket;
import protocol.req.QuitGroupReqPacket;
import protocol.resp.CreateGroupRespPacket;
import protocol.resp.JoinGroupRespPacket;
import protocol.resp.ListGroupMemberRespPacket;
import protocol.resp.LoginRespPacket;
import protocol.resp.LogoutRespPacket;
import protocol.resp.MessageRespPacket;
import protocol.resp.QuitGroupRespPacket;
import serialize.Serializer;
import serialize.SerializerManager;

import java.util.HashMap;
import java.util.Map;

import static protocol.Command.CREATE_GROUP_REQ;
import static protocol.Command.CREATE_GROUP_RESP;
import static protocol.Command.JOIN_GROUP_REQ;
import static protocol.Command.JOIN_GROUP_RESP;
import static protocol.Command.LIST_GROUP_MEMBER_REQ;
import static protocol.Command.LIST_GROUP_MEMBER_RESP;
import static protocol.Command.LOGIN_REQ;
import static protocol.Command.LOGIN_RESP;
import static protocol.Command.LOGOUT_REQ;
import static protocol.Command.LOGOUT_RESP;
import static protocol.Command.MESSAGE_REQ;
import static protocol.Command.MESSAGE_RESP;
import static protocol.Command.QUIT_GROUP_REQ;
import static protocol.Command.QUIT_GROUP_RESP;

/**
 * @author ybd
 * @date 19-7-17
 * @contact yangbingdong1994@gmail.com
 */
public class PacketCodec {

    public static final int MAGIC_NUMBER = 0x12345678;

    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;

    static {
        packetTypeMap = new HashMap<>(16);
        packetTypeMap.put(LOGIN_REQ, LoginReqPacket.class);
        packetTypeMap.put(LOGIN_RESP, LoginRespPacket.class);
        packetTypeMap.put(MESSAGE_REQ, MessageReqPacket.class);
        packetTypeMap.put(MESSAGE_RESP, MessageRespPacket.class);
        packetTypeMap.put(LOGOUT_REQ, LogoutReqPacket.class);
        packetTypeMap.put(LOGOUT_RESP, LogoutRespPacket.class);
        packetTypeMap.put(CREATE_GROUP_REQ, CreateGroupReqPacket.class);
        packetTypeMap.put(CREATE_GROUP_RESP, CreateGroupRespPacket.class);
        packetTypeMap.put(JOIN_GROUP_REQ, JoinGroupReqPacket.class);
        packetTypeMap.put(JOIN_GROUP_RESP, JoinGroupRespPacket.class);
        packetTypeMap.put(QUIT_GROUP_REQ, QuitGroupReqPacket.class);
        packetTypeMap.put(QUIT_GROUP_RESP, QuitGroupRespPacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBER_REQ, ListGroupMemberReqPacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBER_RESP, ListGroupMemberRespPacket.class);
    }

    public static void encode(Packet packet, ByteBuf buf) {
        byte[] bytes = SerializerManager.JSON.serialize(packet);

        buf.writeInt(MAGIC_NUMBER)
           .writeByte(Packet.PROTOCOL_VERSION)
           .writeByte(SerializerManager.JSON.getSerializeType())
           .writeByte(packet.supportCommand())
           .writeInt(bytes.length)
           .writeBytes(bytes);

    }

    public static Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本
        byteBuf.skipBytes(1);

        // 获取序列化方式
        byte serializeType = byteBuf.readByte();
        Serializer serializer = SerializerManager.getByType(serializeType);

        // 获取指令
        byte command = byteBuf.readByte();
        Class<? extends Packet> packetClass = packetTypeMap.get(command);

        // 获取数据包长度
        int dataLength = byteBuf.readInt();

        if (serializer != null && packetClass != null) {
            byte[] bytes = new byte[dataLength];
            byteBuf.readBytes(bytes);
            return serializer.decode(packetClass, bytes);
        }

        return null;
    }
}
