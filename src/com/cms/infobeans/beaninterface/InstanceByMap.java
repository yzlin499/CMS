package com.cms.infobeans.beaninterface;

import com.cms.infobeans.InfoMap;
import com.cms.infobeans.wechat.WCText;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

public interface InstanceByMap {

    static InstanceByMap createInstance(Class<? extends InstanceByMap> bean, Map<String,?> jo){
        try {

            InstanceByMap obj=bean.newInstance();
            createInstance(jo,obj);
            return obj;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    static void createInstance(Map<String,?> valueMap,InstanceByMap obj){
        Class<? extends InstanceByMap> clazz=obj.getClass();
        if(InfoMap.class.equals(clazz)){
            ((InfoMap)obj).setData(valueMap);
            return;
        }
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
            if(valueMap.containsKey(key) || valueMap.containsKey(key=(char)(key.charAt(0)^32)+key.substring(1))) {
                try {
                    Method setMethod = new PropertyDescriptor(fieldName, clazz).getWriteMethod();
                    if(setMethod!=null){
                        Class paramType = setMethod.getParameterTypes()[0];
                        Object value = valueMap.get(key);
                        if (paramType.isInstance(value)) {
                            setMethod.invoke(obj, value);
                        } else if (paramType.equals(int.class) || paramType.equals(Integer.class)) {
                            setMethod.invoke(obj, Integer.parseInt(value.toString()));
                        } else if (paramType.equals(double.class) || paramType.equals(Double.class)) {
                            setMethod.invoke(obj, Double.parseDouble(value.toString()));
                        } else if (paramType.equals(float.class) || paramType.equals(Float.class)) {
                            setMethod.invoke(obj, Float.parseFloat(value.toString()));
                        } else if (paramType.equals(boolean.class) || paramType.equals(Boolean.class)) {
                            setMethod.invoke(obj, Boolean.parseBoolean(value.toString()));
                        } else if (paramType.equals(long.class) || paramType.equals(Long.class)) {
                            setMethod.invoke(obj, Long.parseLong(value.toString()));
                        } else if (paramType.equals(Date.class)) {
                            setMethod.invoke(obj, new Date(Long.parseLong(value.toString())));
                        } else if (paramType.equals(String.class)) {
                            setMethod.invoke(obj, value.toString());
                        }
                    }
                } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                    System.out.println(e.getMessage());
                } catch (IndexOutOfBoundsException e) {}
            }
        }
    }
}
