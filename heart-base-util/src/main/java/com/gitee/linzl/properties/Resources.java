
package com.gitee.linzl.properties;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Properties;

/**
 * cp from mybatis
 * <p>
 * A class to simplify access to resources through the classloader.
 *
 * @author Clinton Begin
 */
public class Resources {
    private static ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();

    /**
     * Charset to use when calling getResourceAsReader.
     * null means use the system default.
     */
    private static Charset charset;

    Resources() {
    }

    /**
     * Returns the default classloader (may be null).
     *
     * @return The default classloader
     */
    public static ClassLoader getDefaultClassLoader() {
        return classLoaderWrapper.defaultClassLoader;
    }

    /**
     * Sets the default classloader
     *
     * @param defaultClassLoader - the new default ClassLoader
     */
    public static void setDefaultClassLoader(ClassLoader defaultClassLoader) {
        classLoaderWrapper.defaultClassLoader = defaultClassLoader;
    }

    /**
     * Returns the URL of the resource on the classpath
     *
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static URL getResourceURL(String resource) throws IOException {
        // issue #625
        return getResourceURL(null, resource);
    }

    /**
     * Returns the URL of the resource on the classpath
     *
     * @param loader   The classloader used to fetch the resource
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static URL getResourceURL(ClassLoader loader, String resource) throws IOException {
        URL url = classLoaderWrapper.getResourceAsURL(resource, loader);
        if (url == null) {
            throw new IOException("Could not find resource " + resource);
        }
        return url;
    }

    /**
     * Returns a resource on the classpath as a Stream object
     *
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static InputStream getResourceAsStream(String resource) throws IOException {
        return getResourceAsStream(null, resource);
    }

    /**
     * Returns a resource on the classpath as a Stream object
     *
     * @param loader   The classloader used to fetch the resource
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
        InputStream in = classLoaderWrapper.getResourceAsStream(resource, loader);
        if (in == null) {
            throw new IOException("Could not find resource " + resource);
        }
        return in;
    }

    /**
     * Returns a resource on the classpath as a Properties object
     *
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static Properties getResourceAsProperties(String resource) throws IOException {
        Properties props = new Properties();
        try (InputStream in = getResourceAsStream(resource)) {
            props.load(in);
        }
        return props;
    }

    /**
     * Returns a resource on the classpath as a Properties object
     *
     * @param loader   The classloader used to fetch the resource
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static Properties getResourceAsProperties(ClassLoader loader, String resource) throws IOException {
        Properties props = new Properties();
        try (InputStream in = getResourceAsStream(loader, resource)) {
            props.load(in);
        }
        return props;
    }

    /**
     * Returns a resource on the classpath as a Reader object
     *
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static Reader getResourceAsReader(String resource) throws IOException {
        Reader reader;
        if (charset == null) {
            reader = new InputStreamReader(getResourceAsStream(resource));
        } else {
            reader = new InputStreamReader(getResourceAsStream(resource), charset);
        }
        return reader;
    }

    /**
     * Returns a resource on the classpath as a Reader object
     *
     * @param loader   The classloader used to fetch the resource
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static Reader getResourceAsReader(ClassLoader loader, String resource) throws IOException {
        Reader reader;
        if (charset == null) {
            reader = new InputStreamReader(getResourceAsStream(loader, resource));
        } else {
            reader = new InputStreamReader(getResourceAsStream(loader, resource), charset);
        }
        return reader;
    }

    /**
     * Returns a resource on the classpath as a File object
     *
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static File getResourceAsFile(String resource) throws IOException {
        return new File(getResourceURL(resource).getFile());
    }

    /**
     * Returns a resource on the classpath as a File object
     *
     * @param loader   - the classloader used to fetch the resource
     * @param resource - the resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static File getResourceAsFile(ClassLoader loader, String resource) throws IOException {
        return new File(getResourceURL(loader, resource).getFile());
    }

    /**
     * Gets a URL as an input stream
     *
     * @param urlString - the URL to get
     * @return An input stream with the data from the URL
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static InputStream getUrlAsStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        return conn.getInputStream();
    }

    /**
     * Gets a URL as a Reader
     *
     * @param urlString - the URL to get
     * @return A Reader with the data from the URL
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static Reader getUrlAsReader(String urlString) throws IOException {
        Reader reader;
        if (charset == null) {
            reader = new InputStreamReader(getUrlAsStream(urlString));
        } else {
            reader = new InputStreamReader(getUrlAsStream(urlString), charset);
        }
        return reader;
    }

    /**
     * Gets a URL as a Properties object
     *
     * @param urlString - the URL to get
     * @return A Properties object with the data from the URL
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static Properties getUrlAsProperties(String urlString) throws IOException {
        Properties props = new Properties();
        try (InputStream in = getUrlAsStream(urlString)) {
            props.load(in);
        }
        return props;
    }

    /**
     * Loads a class
     *
     * @param className - the class to fetch
     * @return The loaded class
     * @throws ClassNotFoundException If the class cannot be found (duh!)
     */
    public static Class<?> classForName(String className) throws ClassNotFoundException {
        return classLoaderWrapper.classForName(className);
    }

    public static Charset getCharset() {
        return charset;
    }

    public static void setCharset(Charset charset) {
        Resources.charset = charset;
    }

    /**
     * 获取cls类加载器中 /WebContent/WEB-INF/路径下的资源文件,递归搜索
     *
     * @param name 资源文件名称
     * @param cls
     * @return
     */
    public static URL getWebInfoResouceURL(String name, Class<?> cls) {
        File targetFile = null;
        String classPath = cls.getResource("/").getFile();
        // 先从外围找
        int index = classPath.indexOf("classes");
        if (index > -1) {
            String outClassPath = classPath.substring(0, index);
            targetFile = findFile(new File(outClassPath), name);
        }

        // 找不到，再深入类文件中看是否有匹配的
        if (targetFile == null && index > -1) {
            // 递归搜索文件
            targetFile = findFile(new File(classPath), name);
        }

        try {
            return Objects.isNull(targetFile) ? null : targetFile.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static File findFile(File targetFile, String name) {
        File[] files = targetFile.listFiles();

        if (Objects.isNull(files)) {
            return null;
        }
        File target = null;
        for (File file : files) {
            if (file.isFile() && file.getName().equalsIgnoreCase(name)) {
                return file;
            }
            target = findFile(file, name);
            if (target != null) {
                return target;
            }
        }
        return target;
    }
}
