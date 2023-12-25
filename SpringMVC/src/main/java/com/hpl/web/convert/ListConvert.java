package com.hpl.web.convert;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/25 22:40
 */
public class ListConvert extends Convert<ArrayList>{

    public ListConvert(Class<ArrayList> type) {
        super(type);
    }

    @Override
    protected Object convert(Object arg) throws Exception {
        return this.type.getConstructor(Collection.class).newInstance(arg);

    }
}
