package com.cms.infobeans;

import com.cms.infobeans.beaninterface.InstanceByMap;
import com.cms.infobeans.beaninterface.MapKey;

public class WeChatBindingInfo implements InstanceByMap {
    private String BindValue;
    private String UserName;
    private int BindType;

    public String getBindValue() {
        return BindValue;
    }

    public void setBindValue(String bindValue) {
        BindValue = bindValue;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getBindType() {
        return BindType;
    }

    public void setBindType(int bindType) {
        BindType = bindType;
    }
}