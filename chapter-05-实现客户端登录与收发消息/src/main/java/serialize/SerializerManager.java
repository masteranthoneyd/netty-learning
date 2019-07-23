package serialize;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ybd
 * @date 19-7-17
 * @contact yangbingdong1994@gmail.com
 */
public enum SerializerManager {
    JSON(Byte.valueOf("1"), new FastJsonSerializer());

    private byte serializeType;
    private Serializer serializer;

    private static Map<Byte, Serializer> map;

    static {
        map = new HashMap<>(16);
        for (SerializerManager value : SerializerManager.values()) {
            map.put(value.getSerializeType(), value.getSerializer());
        }
    }

    SerializerManager(byte serializeType, Serializer serializer) {
        this.serializeType = serializeType;
        this.serializer = serializer;
    }

    public byte[] serialize(Object o) {
        return serializer.encode(o);
    }

    public byte getSerializeType() {
        return serializeType;
    }

    public Serializer getSerializer() {
        return serializer;
    }

    public static Serializer getByType(Byte type) {
        return map.get(type);
    }
}
