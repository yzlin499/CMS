package com.cms.servlets;

import com.cms.frameclass.DisposeClass;
import com.cms.infobeans.Room;
import com.cms.sqltools.SqlKey;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "DisposeRoom",value = "/disposeRoom")
public class DisposeRoom extends DisposeClass<SelectRoom> {

    @Override
    protected void newOperate(Object srcData, SqlSession sqls, JSONArray resultData, JSONArray error) {
        try {
            Room room=new Room((JSONObject)srcData);
            if(room.getRoomName()==null || "".equals(room.getRoomName())){
                JSONObject tempJO=new JSONObject();
                tempJO.put("data",srcData);
                tempJO.put("message","RoomName不能为空");
                error.add(tempJO);
            }else {
                resultData.add(sqls.selectOne(SqlKey.CREATE_ROOM, room));
            }
        }catch (PersistenceException e){
            JSONObject tempJO=new JSONObject();
            tempJO.put("data",srcData);
            tempJO.put("message",e.getCause().getMessage());
            error.add(tempJO);
        }
    }

    @Override
    protected void modifyOperate(Object srcData, SqlSession sqls, JSONArray resultData, JSONArray error) {
        Room temp=new Room((JSONObject)srcData);
        try{
            if(!temp.isNotNull()){
                JSONObject tempJO=new JSONObject();
                tempJO.put("RID",temp.getRoomID());
                tempJO.put("message","数据不完整");
                error.add(tempJO);
                return;
            }
            sqls.selectOne(SqlKey.UPDATE_ROOM,temp);
            resultData.add(temp.getRoomID());
        }catch (PersistenceException e){
            JSONObject tempJO=new JSONObject();
            tempJO.put("RID",temp.getRoomID());
            tempJO.put("message",e.getCause().getMessage());
            error.add(tempJO);
        }
    }

    @Override
    protected void deleteOperate(Object srcData, SqlSession sqls, JSONArray resultData, JSONArray error) {
        try{
            sqls.delete(SqlKey.DELETE_ROOM,srcData);
            resultData.add(srcData);
        }catch (PersistenceException e){
            JSONObject tempJO=new JSONObject();
            tempJO.put("RID",srcData);
            tempJO.put("message",e.getCause().getMessage());
            error.add(tempJO);
        }
    }
}