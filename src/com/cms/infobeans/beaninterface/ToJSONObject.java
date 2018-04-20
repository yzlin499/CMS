package com.cms.infobeans.beaninterface;

import net.sf.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

public interface ToJSONObject {
    default JSONObject toJSON(){
        return autoToJSON(this);
    }
    static JSONObject autoToJSON(Object beanObject){
        JSONObject jo=new JSONObject();
        Class bean= beanObject.getClass();
        String method[]= Stream.of(bean.getMethods())
                .map(m->m.getName())
                .filter(m->m.matches("get(?!Class$)[\\S]+"))
                .sorted()
                .toArray(String[]::new);
        Stream.of(bean.getDeclaredFields())
                .filter(m->{
                    MapKey mapKey =m.getAnnotation(MapKey.class);
                    if(mapKey ==null){
                        return true;
                    }else{
                        return mapKey.create();
                    }
                }).filter(m->{
            char[] ch = m.getName().toCharArray();
            ch[0]&=0xDF;
            return Arrays.binarySearch(method,"get"+new String(ch))>=0;
        }).forEach(m->{
            String key;
            try {
                char[] ch = m.getName().toCharArray();
                ch[0]&=0xDF;
                Method methodKey=bean.getMethod("get"+new String(ch));
                MapKey mapKey =m.getAnnotation(MapKey.class);
                if(mapKey ==null){
                    key=m.getName();
                }else{
                    key= mapKey.value();
                }

                Object result=methodKey.invoke(beanObject);
                if(result==null){

                }else if(result instanceof Date){
                    jo.put(key,((Date)result).getTime());
                }else if(result instanceof Integer){
                    jo.put(key,result);
                }else{
                    jo.put(key,result.toString());
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return jo;
    }
}
