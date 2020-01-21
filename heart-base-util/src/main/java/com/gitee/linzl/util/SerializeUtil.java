package com.gitee.linzl.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 序列化工具
 */
@Slf4j
public class SerializeUtil {
    /**
     * 序列化
     *
     * @param object
     */
    public static byte[] serialize(Object object) {
        if (!(object instanceof Serializable)) {
            throw new IllegalArgumentException("入参要求可序列化（Serializable），请检查类" + object.getClass().getName());
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (Exception e) {
            log.error("序列化异常[Object:{}]", JSON.toJSONString(object));
        }
        return null;
    }

    /**
     * 反序列化
     *
     * @param bytes
     */
    public static Object unSerialize(byte[] bytes) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            log.error("反序列化异常[Object:{}]", JSON.toJSONString(bytes));
        }
        return null;
    }
}
