package com.hpl.web.convert;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/25 22:40
 */
public class ShortConvert extends Convert<Short>{


    public ShortConvert(Class<Short> type) {
        super(type);
    }

    @Override
    public Object convert(Object arg) throws Exception {
        return defaultConvert(arg.toString());
    }
}
