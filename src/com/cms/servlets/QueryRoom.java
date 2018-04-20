package com.cms.servlets;

import com.cms.frameclass.FromAPIServlet;
import com.cms.frameclass.StateCode;
import com.cms.infobeans.FilterRoomParam;
import com.cms.infobeans.QueryRoomParam;
import com.cms.sqltools.SqlSessionManagement;
import com.cms.tools.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.exceptions.PersistenceException;

import javax.servlet.annotation.WebServlet;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cms.sqltools.SqlKey.FILTER_ROOM;
import static com.cms.sqltools.SqlKey.QUERY_ROOM;

@WebServlet(name = "QueryRoom",value = "/queryRoom")
public class QueryRoom extends FromAPIServlet<QueryRoomParam>{
    private SqlSessionManagement<List<Map>> sqls=SqlSessionManagement.getInstance();
    public QueryRoom(){
        setContentType("application/json");
    }

    @Override
    final protected String responseText(QueryRoomParam dataBean) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("state", StateCode.COMPLETE);
        responseJSON.put("message","查询成功");
        JSONArray dataNode=new JSONArray();
        try {
            dataNode.addAll(
                    sqls.customSqlSession(sqlSession ->sqlSession.selectList(QUERY_ROOM, dataBean))
                        .stream()
                        .map(JSONObject::fromObject).collect(Collectors.toList())
            );
        }catch (PersistenceException e){
            responseJSON.put("state", StateCode.DB_ERROR);
            responseJSON.put("message",e.getCause().getMessage());
        }
        responseJSON.put("data",dataNode);
        return Tools.cnToUnicode(responseJSON.toString());
    }
}
