package com.cms.servlets;

import com.cms.infobeans.wechat.SignInfo;
import com.cms.tools.Tools;
import com.cms.wechat.WeChatJSUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "WeChatSign",value = "/weChatSign")
public class WeChatSign extends HttpServlet {
    private WeChatJSUtil weChatJSUtil=WeChatJSUtil.getInstance();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SignInfo signInfo=weChatJSUtil.sign(req.getParameter("url"));
        resp.getWriter()
                .print(Tools.cnToUnicode(signInfo.toJSON().toString()));
    }
}
