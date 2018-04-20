package com.cms.servlets;

import com.cms.exceptions.CMSException;
import com.cms.exceptions.DBException;
import com.cms.frameclass.FilterClass;
import com.cms.infobeans.FilterRoomParam;
import net.sf.json.JSONArray;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.annotation.WebServlet;

import static com.cms.sqltools.SqlKey.FILTER_ROOM;

@WebServlet(name="FilterRoom",value ="/filterRoom")
public class FilterRoom extends FilterClass<FilterRoomParam> {
    @Override
    protected void responseData(FilterRoomParam req, SqlSession sqls, JSONArray resultData)throws CMSException {
        try {
            resultData.addAll(sqls.selectList(FILTER_ROOM, req));
        }catch (PersistenceException e){
            throw new DBException(e.getCause().getMessage());
        }
    }
}
