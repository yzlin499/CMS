package com.cms.frameclass;

import com.cms.sqltools.SqlSessionManagement;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;

/**
 * 检测缓存用
 */
public class CacheState {
    private static class InstanceClass {
        private static final CacheState instance=new CacheState();
    }
    private CacheState(){}
    private HashMap<Class<? extends HttpServlet>,Boolean> stateMap=new HashMap<>();

    public static CacheState getInstance(){
        return InstanceClass.instance;
    }

    public void dataUpdate(Class<? extends HttpServlet> servletClass){
        stateMap.put(servletClass,true);
    }

    public boolean isUpdate(Class<? extends HttpServlet> servletClass){
        if(stateMap.containsKey(servletClass)){
            return stateMap.put(servletClass,false);
        }else{
            stateMap.put(servletClass,false);
            return true;
        }
    }

    public void resetAllCache(){
        for(Class c:stateMap.keySet()){
            stateMap.put(c,true);
        }
    }
}
