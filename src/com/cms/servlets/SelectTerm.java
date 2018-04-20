package com.cms.servlets;

import com.cms.frameclass.SelectClass;
import com.cms.infobeans.Term;
import com.cms.sqltools.SqlKey;
import net.sf.json.JSONArray;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.annotation.WebServlet;
import java.util.List;

@WebServlet(name="SelectTerm",value = "/selectTerm")
public class SelectTerm extends SelectClass {
    @Override
    protected JSONArray SpecificSelect(SqlSession sqls) {
        JSONArray dataNode = new JSONArray();
        List<Term> result = sqls.selectList(SqlKey.SELECT_TERM);
        result.forEach(r -> dataNode.add(r.toJSON()));
        return dataNode;
    }
}
