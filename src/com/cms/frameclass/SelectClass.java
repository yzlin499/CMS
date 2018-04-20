package com.cms.frameclass;

import com.cms.exceptions.CMSException;
import com.cms.sqltools.SqlSessionManagement;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cms.tools.Tools.cnToUnicode;

public abstract class SelectClass extends HttpServlet {
    private SqlSessionManagement<JSONArray> ssm=SqlSessionManagement.getInstance();
    private String result="{}";
    private CacheState cacheState=CacheState.getInstance();
    private long time=System.currentTimeMillis();
    private Class<? extends SelectClass> sonClass=getClass();

    @Override
    final protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (cacheState.isUpdate(sonClass) || (System.currentTimeMillis() - time > 3600000)) {
            time = System.currentTimeMillis();
            JSONObject resultJSONObject = new JSONObject();
            resultJSONObject.put("state", StateCode.COMPLETE);
            resultJSONObject.put("message", "查询成功");
            resultJSONObject.put("data",ssm.customSqlSession(h-> {
                try {
                    return SpecificSelect(h);
                } catch (CMSException e) {
                    resultJSONObject.put("state", e.getState());
                    resultJSONObject.put("message",e.getMessage());
                    return null;
                }
            }));
            result=cnToUnicode(resultJSONObject.toString());
        }
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().print(result);
    }

    @Override
    final protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    protected abstract JSONArray SpecificSelect(SqlSession sqls) throws CMSException;
}
