package com.cms.wechat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="WeChatMain",value = "/weChatMain")
public class WeChatMain extends HttpServlet {
    WeChatEventPool weChatEventPool=WeChatEventPool.getInstance();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        weChatEventPool.newMsg(new WeChatIO(req,resp));
        weChatEventPool.addWeChatEvent(WCSelectRoom.class);
    }
}
