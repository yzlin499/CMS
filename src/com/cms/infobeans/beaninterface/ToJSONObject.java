package com.cms.infobeans.beaninterface;

import net.sf.json.JSONObject;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public interface ToJSONObject {
    default JSONObject toJSON(){
        return autoToJSON(this);
    }
    static JSONObject autoToJSON(Object beanObject){
        JSONObject jo=new JSONObject();
        Class clazz= beanObject.getClass();
        List<Field> list=new LinkedList<>();
        Class superClass=clazz;
        do{
            list.addAll(Arrays.asList(superClass.getDeclaredFields()));
        }while((superClass=superClass.getSuperclass())!=null);

        for(Field field:list){
            String key;
            String fieldName=field.getName();
            MapKey mapKey =field.getAnnotation(MapKey.class);
            if(mapKey==null){
                key=fieldName;
            }else if(!mapKey.create()){
                continue;
            }else{
                key=mapKey.value();
                key=key.equals("")?fieldName:key;
            }
            try {
                Method getMethod=new PropertyDescriptor(fieldName, clazz).getReadMethod();
                Object result=getMethod.invoke(beanObject);
                if(result==null){

                }else if(result instanceof Date){
                    jo.put(key,((Date)result).getTime());
                }else if(result instanceof Number || result instanceof Boolean){
                    jo.put(key,result);
                }else{
                    jo.put(key,result.toString());
                }
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return jo;
    }
}
