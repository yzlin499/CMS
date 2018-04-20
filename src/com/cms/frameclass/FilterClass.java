package com.cms.frameclass;

import com.cms.exceptions.CMSException;
import com.cms.infobeans.beaninterface.InstanceByMap;
import com.cms.sqltools.SqlSessionManagement;
import com.cms.tools.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;

public abstract class FilterClass<T extends InstanceByMap> extends FromAPIServlet<T> {
    private SqlSessionManagement<Boolean> sqls=SqlSessionManagement.getInstance();
    public FilterClass(){
        setContentType("application/json");
    }

    @Override
    final protected String responseText(T dataBean) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("state", StateCode.COMPLETE);
        responseJSON.put("message","查询成功");
        JSONArray dataNode=new JSONArray();
        sqls.customSqlSession(sqlSession -> {
            try {
                responseData(dataBean,sqlSession,dataNode);
            } catch (CMSException e) {
                responseJSON.put("state", e.getState());
                responseJSON.put("message",e.getMessage());
            }
            return true;
        });
        responseJSON.put("data",dataNode);
        return Tools.cnToUnicode(responseJSON.toString());
    }

    protected abstract void responseData(T dataBean, SqlSession sqls,JSONArray resultData) throws CMSException;
}
