package com.hpl.web.convert;

import java.util.Date;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/25 22:40
 */
public class DateConvert extends Convert<Date>{


    public DateConvert(Class<Date> type) {
        super(type);
    }

    @Override
    public Object convert(Object arg) throws Exception {
        return defaultConvert(arg.toString());
    }
}
