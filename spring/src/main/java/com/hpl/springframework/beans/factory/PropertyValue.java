package com.hpl.springframework.beans.factory;

/**
 *
 * @Author: huangpenglong
 * @Date: 2024/2/8 20:16
 */
public class PropertyValue {

    /**
     * name: 属性名
     * value: 属性值
     */
    private final String name;

    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

}
