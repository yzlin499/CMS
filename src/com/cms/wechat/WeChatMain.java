package com.cms.wechat;

import com.cms.infobeans.wechat.WCText;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="WeChatMain",value = "/weChatMain")
public class WeChatMain extends HttpServlet {
    private static WeChatEventPool weChatEventPool;
    static{
        weChatEventPool=WeChatEventPool.getInstance();
        weChatEventPool.addWeChatEvent(WCSelectRoom.class);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        weChatEventPool.newMsg(new WeChatIO(req,resp));
    }
}
