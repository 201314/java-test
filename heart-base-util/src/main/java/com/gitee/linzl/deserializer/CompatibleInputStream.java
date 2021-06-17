package com.gitee.linzl.deserializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.Objects;

/**
 * cp from https://stackoverflow.com/a/1816711/6507948
 * <p>
 * <p>
 * // Deserialize a string and date from a file.
 * FileInputStream in = new FileInputStream("tmp");
 * // 反序列化时使用上面的CompatibleInputStream即可
 * ObjectInputStream s = new CompatibleInputStream(in);
 * String today = (String)s.readObject();
 * Date date = (Date)s.readObject();
 *
 * @author linzl
 * @date 2021/6/17
 */
public class CompatibleInputStream extends ObjectInputStream {
    private static Logger logger = LoggerFactory.getLogger(CompatibleInputStream.class);

    public CompatibleInputStream(InputStream in) throws IOException {
        super(in);
    }

    @Override
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        ObjectStreamClass resultClassDescriptor = super.readClassDescriptor();
        Class localClass;
        try {
            localClass = Class.forName(resultClassDescriptor.getName());
        } catch (ClassNotFoundException e) {
            logger.error("No local class for " + resultClassDescriptor.getName(), e);
            return resultClassDescriptor;
        }
        ObjectStreamClass localClassDescriptor = ObjectStreamClass.lookup(localClass);
        if (Objects.isNull(localClassDescriptor)) {
            return resultClassDescriptor;
        }

        long localSUID = localClassDescriptor.getSerialVersionUID();
        long streamSUID = resultClassDescriptor.getSerialVersionUID();
        if (streamSUID != localSUID) {
            StringBuffer buffer = new StringBuffer("Overriding serialized class version mismatch:");
            buffer.append("local serialVersionUID = ")
                    .append(localSUID)
                    .append(",")
                    .append("stream serialVersionUID = ")
                    .append(streamSUID);
            Exception e = new InvalidClassException(buffer.toString());
            logger.error("Potentially Fatal Deserialization Operation.", e);
            // Use local class descriptor for deserialization
            resultClassDescriptor = localClassDescriptor;
        }
        return resultClassDescriptor;
    }
}