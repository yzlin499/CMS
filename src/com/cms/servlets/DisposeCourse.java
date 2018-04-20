package com.cms.servlets;

import com.cms.frameclass.DisposeClass;
import com.cms.infobeans.Course;
import com.cms.sqltools.SqlKey;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "DisposeCourse",value = "/disposeCourse")
public class DisposeCourse extends DisposeClass {

    @Override
    protected void newOperate(Object srcData,SqlSession sqls,JSONArray dataNode,JSONArray errorNode){
        try {
            dataNode.add(sqls.selectOne(SqlKey.CREATE_COURSE, new Course((JSONObject)srcData)));
        }catch (PersistenceException e){
            JSONObject tempJO=new JSONObject();
            tempJO.put("data",srcData);
            tempJO.put("message",e.getCause().getMessage());
            errorNode.add(tempJO);
        }
    }

    @Override
    protected void modifyOperate(Object srcData,SqlSession sqls,JSONArray dataNode,JSONArray errorNode){
        Course temp=new Course((JSONObject)srcData);
        try{
            if(!temp.isNotNull()){
                JSONObject tempJO=new JSONObject();
                tempJO.put("CID",temp.getCourseID());
                tempJO.put("message","数据不完整");
                errorNode.add(tempJO);
                return;
            }
            sqls.selectOne(SqlKey.UPDATE_COURSE,temp);
            dataNode.add(temp.getCourseID());
        }catch (PersistenceException e){
            JSONObject tempJO=new JSONObject();
            tempJO.put("CID",temp.getCourseID());
            tempJO.put("message",e.getCause().getMessage());
            errorNode.add(tempJO);
        }
    }

    @Override
    protected void deleteOperate(Object srcData,SqlSession sqls,JSONArray resultData,JSONArray error){
        try{
            sqls.delete(SqlKey.DELETE_COURSE,srcData);
            resultData.add(srcData);
        }catch (PersistenceException e){
            JSONObject tempJO=new JSONObject();
            tempJO.put("CID",srcData);
            tempJO.put("message",e.getCause().getMessage());
            error.add(tempJO);
        }
    }
}