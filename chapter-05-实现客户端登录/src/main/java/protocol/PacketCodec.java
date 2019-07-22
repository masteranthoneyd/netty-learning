package protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import serialize.Serializer;
import serialize.SerializerManager;

import java.util.HashMap;
import java.util.Map;

import static protocol.Command.LOGIN_REQUEST;
import static protocol.Command.LOGIN_RESPONSE;

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
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
    }

    public static ByteBuf encode(Packet packet) {
        // 1. 获取 ByteBuf 对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        // 2. 序列化 Java 对象
        byte[] bytes = SerializerManager.JSON.serialize(packet);

        // 3. 编码
        byteBuf.writeInt(MAGIC_NUMBER)
               .writeByte(Packet.PROTOCOL_VERSION)
               .writeByte(SerializerManager.JSON.getSerializeType())
               .writeByte(packet.supportCommand())
               .writeInt(bytes.length)
               .writeBytes(bytes);

        return byteBuf;
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
