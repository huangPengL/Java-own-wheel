package com.hpl.web.convert;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/25 22:40
 */
public class ByteConvert extends Convert<Byte> {


    public ByteConvert(Class<Byte> type) {
        super(type);
    }

    @Override
    public Object convert(Object arg) throws Exception {
        return defaultConvert(arg.toString());
    }
}
