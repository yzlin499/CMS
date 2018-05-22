package com.cms.infobeans;

import com.cms.infobeans.beaninterface.InstanceByMap;

import java.util.Map;

public class InfoMap implements InstanceByMap {
    private Map data;

    public void setData(Map data) {
        this.data = data;
    }

    public Object get(Object key){
        return data.get(key);
    }
}
