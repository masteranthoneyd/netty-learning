package protocol;

import io.netty.buffer.ByteBuf;
import protocol.req.LoginReqPacket;
import protocol.req.MessageReqPacket;
import protocol.resp.LoginRespPacket;
import protocol.resp.MessageRespPacket;
import serialize.Serializer;
import serialize.SerializerManager;

import java.util.HashMap;
import java.util.Map;

import static protocol.Command.LOGIN_REQ;
import static protocol.Command.LOGIN_RESP;
import static protocol.Command.MESSAGE_REQ;
import static protocol.Command.MESSAGE_RESP;

/**
 * @author ybd
 * @date 19-7-17
 * @contact yangbingdong1994@gmail.com
 */
public class PacketCodec {

    private static final int MAGIC_NUMBER = 0x12345678;

    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;

    static {
        packetTypeMap = new HashMap<>(16);
        packetTypeMap.put(LOGIN_REQ, LoginReqPacket.class);
        packetTypeMap.put(LOGIN_RESP, LoginRespPacket.class);
        packetTypeMap.put(MESSAGE_REQ, MessageReqPacket.class);
        packetTypeMap.put(MESSAGE_RESP, MessageRespPacket.class);
    }

    public static ByteBuf encode(Packet packet, ByteBuf buf) {
        byte[] bytes = SerializerManager.JSON.serialize(packet);

        buf.writeInt(MAGIC_NUMBER)
           .writeByte(Packet.PROTOCOL_VERSION)
           .writeByte(SerializerManager.JSON.getSerializeType())
           .writeByte(packet.supportCommand())
           .writeInt(bytes.length)
           .writeBytes(bytes);

        return buf;
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
