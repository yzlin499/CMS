package com.cms.servlets;

import com.cms.exceptions.CMSException;
import com.cms.frameclass.SelectClass;
import com.cms.infobeans.Room;
import com.cms.infobeans.UserInfo;
import com.cms.sqltools.SqlKey;
import net.sf.json.JSONArray;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.annotation.WebServlet;
import java.util.List;

@WebServlet(name = "SelectPerson",value = "/selectPerson")
public class SelectPerson extends SelectClass {
    @Override
    protected JSONArray SpecificSelect(SqlSession sqls) throws CMSException {
        JSONArray dataNode=new JSONArray();
        List<UserInfo> result= sqls.selectList(SqlKey.SELECT_PERSON);
        result.forEach(r->dataNode.add(r.toJSON()));
        return dataNode;
    }
}
