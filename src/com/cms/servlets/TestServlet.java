package com.cms.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.stream.Stream;

import com.cms.infobeans.beaninterface.InstanceByMap;
import com.cms.tools.NetTools;
import com.cms.wechat.W;
import net.sf.json.JSONObject;

@WebServlet(name="test",value = "/test")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print(NetTools.sendGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+
                W.Configure.AppID+"&secret="+W.Configure.AppSecret));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
