package com.cms.frameclass;

import com.cms.infobeans.beaninterface.InstanceByMap;
import com.cms.sqltools.SqlSessionManagement;
import com.cms.tools.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public abstract class FromAPIServlet<T extends InstanceByMap> extends HttpServlet {
    private Class<T> infoBean=getFilterBean();
    private Map<String,String[]> paramMap;
    private String contentType="text/html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        theFunction(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        theFunction(req, resp);
    }

    private void theFunction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Map<String, String> data=new HashMap<>();
        paramMap=req.getParameterMap();
        paramMap.forEach((k,v)-> data.put(k,v[0]));
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
