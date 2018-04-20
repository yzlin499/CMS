package com.cms.frameclass;

import com.cms.infobeans.beaninterface.InstanceByMap;
import com.cms.tools.Tools;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class FromJSONAPIServlet <T extends InstanceByMap> extends HttpServlet {
    private Class<T> infoBean=getFilterBean();
    private Map<String,String[]> paramMap;
    private String contentType="text/html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> data=new HashMap<>();
        paramMap=req.getParameterMap();
        paramMap.forEach((k,v)-> {
            try {
                data.put(k,java.net.URLDecoder.decode(v[0],Objects.toString(req.getCharacterEncoding(),"UTF-8")));
            } catch (UnsupportedEncodingException e) {
            }
        });
        theFunction(data,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> data=new HashMap<>();
        JSONObject jo= Tools.dataToJSONObject(req);
        if(jo==null){
            paramMap=req.getParameterMap();
            paramMap.forEach((k,v)-> {
                try {
                    data.put(k,java.net.URLDecoder.decode(v[0],Objects.toString(req.getCharacterEncoding(),"UTF-8")));
                } catch (UnsupportedEncodingException e) {
                }
            });
        }else{
            for(Object key:jo.keySet()){
                data.put(key.toString(),jo.get(key).toString());
            }
        }
        theFunction(data,resp);
    }

    private void theFunction(Map<String, String> data,HttpServletResponse resp) throws ServletException, IOException{
        T reqData=(T) InstanceByMap.createInstance(infoBean,data);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType(contentType+";charset=UTF-8");
        resp.getWriter().print(responseText(reqData));
        paramMap=null;
    }

    protected abstract String responseText(T dataBean);

    /**
     * 获得泛型的class
     * @return
     */
    private Class<T> getFilterBean(){
        try {
            Type[] params = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
            return (Class<T>) params[0];
        }catch (ClassCastException e){
            System.out.println(e.getMessage());
            System.out.println("多半是你的FilterClass的子类没有定义泛型");
            return null;
        }
    }

    /**
     * 获取其他参数
     * @param key
     * @return
     */
    final protected String [] getParam(String key){
        return paramMap.get(key);
    }

    final protected void setContentType(String contentType){
        this.contentType=contentType;
    }
}
