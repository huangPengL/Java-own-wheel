package com.hpl.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/15 17:05
 */
public interface Resource {

    /**
     * 提供获取 InputStream 流的方法（例如可以实现三种不同的流文件操作：classPath、FileSystem、URL）
     * @return
     * @throws IOException
     */
    InputStream getInputStream() throws IOException;
}
