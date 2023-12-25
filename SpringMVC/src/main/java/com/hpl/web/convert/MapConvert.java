package com.hpl.web.convert;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/25 22:40
 */
public class MapConvert extends Convert<HashMap>{

    public MapConvert(Class<HashMap> type) {
        super(type);
    }

    @Override
    protected Object convert(Object arg) throws Exception {
        return this.type.getConstructor(Map.class).newInstance(arg);
    }
}
