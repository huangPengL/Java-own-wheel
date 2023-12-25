package com.hpl.web.convert;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/25 22:40
 */
public class CollectionConvert extends Convert<Collection>{

    public CollectionConvert(Class<Collection> type) {
        super(type);
    }

    @Override
    protected Object convert(Object arg) throws Exception {

        return ArrayList.class.getConstructor(Collection.class).newInstance(arg);
    }
}
