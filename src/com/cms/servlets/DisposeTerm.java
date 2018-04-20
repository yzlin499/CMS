package com.cms.servlets;

import com.cms.frameclass.DisposeClass;
import com.cms.infobeans.Term;
import com.cms.sqltools.SqlKey;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.annotation.WebServlet;

@WebServlet(name="DisposeTerm",value = "/disposeTerm")
public class DisposeTerm extends DisposeClass<SelectTerm> {
    @Override
    protected void newOperate(Object srcData, SqlSession sqls, JSONArray resultData, JSONArray error) {
        try {
            resultData.add(sqls.selectOne(SqlKey.CREATE_TERM, new Term((JSONObject)srcData)));
        }catch (PersistenceException e){
            JSONObject tempJO=new JSONObject();
            tempJO.put("data",srcData);
            tempJO.put("message",e.getCause().getMessage());
            error.add(tempJO);
        }
    }

    @Override
    protected void modifyOperate(Object srcData, SqlSession sqls, JSONArray resultData, JSONArray error) {
        Term temp=new Term((JSONObject)srcData);
        try{
            if(!temp.isNotNull()){
                JSONObject tempJO=new JSONObject();
                tempJO.put("TID",temp.getTermID());
                tempJO.put("message","数据不完整");
                error.add(tempJO);
                return;
            }
            sqls.selectOne(SqlKey.UPDATE_TERM,temp);
            resultData.add(temp.getTermID());
        }catch (PersistenceException e){
            JSONObject tempJO=new JSONObject();
            tempJO.put("TID",temp.getTermID());
            tempJO.put("message",e.getCause().getMessage());
            error.add(tempJO);
        }
    }

    @Override
    protected void deleteOperate(Object srcData, SqlSession sqls, JSONArray resultData, JSONArray error) {
        try{
            sqls.delete(SqlKey.DELETE_TERM,srcData);
            resultData.add(srcData);
        }catch (PersistenceException e){
            JSONObject tempJO=new JSONObject();
            tempJO.put("TID",srcData);
            tempJO.put("message",e.getCause().getMessage());
            error.add(tempJO);
        }
    }
}
