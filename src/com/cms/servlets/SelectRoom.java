package com.cms.servlets;

import com.cms.frameclass.SelectClass;
import com.cms.infobeans.Room;
import com.cms.sqltools.SqlKey;
import net.sf.json.JSONArray;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.annotation.WebServlet;
import java.util.List;

@WebServlet(name = "selectRoom",value = "/selectRoom")
public class SelectRoom extends SelectClass {
    @Override
    protected JSONArray SpecificSelect(SqlSession sqls) {
        JSONArray dataNode=new JSONArray();
        List<Room> result= sqls.selectList(SqlKey.SELECT_ROOM);
        result.forEach(r->dataNode.add(r.toJSON()));
        return dataNode;
    }
}
