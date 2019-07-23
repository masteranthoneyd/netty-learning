package serialize;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.alibaba.fastjson.JSON.toJSONBytes;

/**
 * @author ybd
 * @date 19-7-17
 * @contact yangbingdong1994@gmail.com
 */
public class FastJsonSerializer implements Serializer {

    @Override
    public byte[] encode(Object object) {
        return toJSONBytes(object);
    }

    @Override
    public <T> T decode(Class<T> clazz, byte[] bytes) {
        return parseObject(bytes, clazz);
    }
}
