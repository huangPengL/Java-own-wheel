package com.hpl.spring.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Author: huangpenglong
 * @Date: 2024/1/3 14:34
 */
@ConfigurationProperties(prefix = "web")
@EnableConfigurationProperties(MultipartFileProperties.class)
public class MultipartFileProperties {

    private Long fileSize;

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}