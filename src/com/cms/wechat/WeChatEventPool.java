package com.cms.wechat;

import com.cms.infobeans.wechat.WCInfo;
import com.cms.wechat.event.WeChatEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeChatEventPool {
    private static class InstanceClass {
        private static final WeChatEventPool instance=new WeChatEventPool();
    }
    private WeChatEventPool(){}
    public static WeChatEventPool getInstance(){
        return InstanceClass.instance;
    }
    private Map<String,ArrayList<WeChatEvent>> eventMap=new HashMap<>();

    public boolean addWeChatEvent(WeChatEvent weChatEvent){
        String clazz=weChatEvent.getClass().getSimpleName();
        clazz=clazz.substring(2,clazz.length()-5).toLowerCase();
        return getList(clazz).add(weChatEvent);
    }

    public boolean addWeChatEvent(Class<? extends WeChatEvent> weChatEvent){
        try {
            String clazz=weChatEvent.getSimpleName();
            clazz=clazz.substring(2,clazz.length()-5).toLowerCase();
            return getList(clazz).add(weChatEvent.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeWeChatEvent(WeChatEvent weChatEvent){
        String clazz=weChatEvent.getClass().getSimpleName();
        clazz=clazz.substring(2,clazz.length()-5).toLowerCase();
        return getList(clazz).remove(weChatEvent);
    }

    public void newMsg(WeChatIO weChatIO){
        WCInfo wcInfo=weChatIO.getInfo();
        for(WeChatEvent weChatEvent:getList(wcInfo.getMsgType())){
            if(weChatEvent.filter(weChatIO)){
                weChatEvent.disposeMag(weChatIO);
                break;
            }
        }
        weChatIO.close();
    }

    private List<WeChatEvent> getList(String type){
        if(!eventMap.containsKey(type)){
            eventMap.put(type,new ArrayList<>());
        }
        return eventMap.get(type);
    }
}
