package com.cms.frameclass;

import com.cms.exceptions.CMSException;
import com.cms.exceptions.NoJSONException;
import com.cms.sqltools.SqlSessionManagement;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * "New","Modify","Delete"接口类的父类
 * @author yzlin
 */
public abstract class DisposeClass<T extends SelectClass> extends JSONAPIServlet{
    private static SqlSessionManagement<Boolean> sqlSM=SqlSessionManagement.getInstance();
    private static CacheState cacheState=CacheState.getInstance();
    private JSONObject responseJSON;
    private Class<T> selectClass=getFilterBean();
    private Operate [] operates={this::newOperate,this::modifyOperate,this::deleteOperate};
    private String[] operateString={"New","Modify","Delete"};

    /**
     * 一个为了简化算法搞出来的接口，没什么卵用，这个接口只用于当前文件
     */
    private interface Operate{
        void operation(Object srcData,SqlSession sqls,JSONArray resultData,JSONArray error);
    }

    @Override
    final protected JSONObject APIFunction(JSONObject data) throws ServletException, IOException,CMSException {
        if(selectClass!=null){
            cacheState.dataUpdate(selectClass);
        }
        responseJSON=new JSONObject();
        for(String op:operateString){
            if(data.containsKey(op) && (!((JSON)data.get(op)).isArray())){
                throw new NoJSONException("json格式错误");
            }
        }
        responseJSON.put("state",StateCode.COMPLETE);
        responseJSON.put("message","数据传输成功");
        sqlSM.customSqlSession(sqls->{
            JSONObject newNode;
            JSONArray dataNode;
            JSONArray errorNode ;
            for(int i=0;i<operateString.length;i++){
                if(data.containsKey(operateString[i])) {
                    newNode = new JSONObject();
                    dataNode = new JSONArray();
                    errorNode = new JSONArray();
                    for (Object srcData : data.getJSONArray(operateString[i])) {
                        operates[i].operation(srcData, sqls, dataNode, errorNode);
                    }
                    newNode.put("data", dataNode);
                    newNode.put("error", errorNode);
                    responseJSON.put(operateString[i], newNode);
                }
            }
            return true;
        });
        return responseJSON;
    }

    /**
     * 获得泛型的class
     * @return
     */
    private Class<T> getFilterBean(){
        try {
            Type[] params = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
            return (Class<T>) params[0];
        }catch (ClassCastException e){
            System.out.println("多半是你的DisposeClass的子类没有定义泛型");
            return null;
        }
    }

    protected abstract void newOperate(Object srcData,SqlSession sqls,JSONArray resultData,JSONArray error);
    protected abstract void modifyOperate(Object srcData, SqlSession sqls,JSONArray resultData,JSONArray error);
    protected abstract void deleteOperate(Object srcData, SqlSession sqls,JSONArray resultData,JSONArray error);
}
