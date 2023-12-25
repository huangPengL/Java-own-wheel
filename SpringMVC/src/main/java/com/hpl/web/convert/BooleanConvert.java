package com.hpl.web.convert;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/25 22:40
 */
public class BooleanConvert extends Convert<Boolean>{

    public BooleanConvert(Class<Boolean> type) {
        super(type);
    }

    @Override
    public Object convert(Object arg) throws Exception {

        return defaultConvert(arg.toString());
    }
}
