package com.cms.servlets;

import com.cms.frameclass.FromJSONAPIServlet;
import com.cms.frameclass.StateCode;
import com.cms.infobeans.InfoMap;
import com.cms.infobeans.WeChatBindingInfo;
import com.cms.infobeans.beaninterface.InstanceByMap;
import com.cms.sqltools.SqlSessionManagement;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "WeChatBinding",value = "/weChatBinding")
public class WeChatBinding extends FromJSONAPIServlet<WeChatBindingInfo> {
    private SqlSessionManagement sqls=SqlSessionManagement.getInstance();
    private HttpSession tempSession;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        tempSession=req.getSession();
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        tempSession=req.getSession();
        super.doPost(req, resp);
    }

    @Override
    protected String responseText(WeChatBindingInfo dataBean) {
        JSONObject resultJ=new JSONObject();
        if((boolean)tempSession.getAttribute("loginState")){
            resultJ.put("state",StateCode.COMPLETE);
            resultJ.put("message","绑定成功");
            sqls.customSqlSession(q->{

                return null;
            });
        }else{
            resultJ.put("state",StateCode.FORBIDDEN);
            resultJ.put("message","未登陆");
        }

        return null;
    }
}
