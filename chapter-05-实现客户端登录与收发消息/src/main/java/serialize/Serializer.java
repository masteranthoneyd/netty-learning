package serialize;

/**
 * @author ybd
 * @date 19-7-17
 * @contact yangbingdong1994@gmail.com
 */
public interface Serializer {

    byte[] encode(Object object);

    <T> T decode(Class<T> clazz, byte[] bytes);
}
