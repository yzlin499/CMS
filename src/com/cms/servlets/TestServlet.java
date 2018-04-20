package com.cms.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.stream.Stream;

import com.cms.infobeans.beaninterface.InstanceByMap;
import net.sf.json.JSONObject;

@WebServlet(name="test",value = "/test")
public class TestServlet extends HttpServlet {
    boolean aaa=true;
    @Override
    final protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession a=req.getSession();
//        a.setMaxInactiveInterval();
        a.invalidate();
        a.invalidate();
//        a.getAttribute()
//        Cookie n=new Cookie("qwe","asd");
//        n.setMaxAge(30);
//        resp.addCookie(n);
//        Stream.of(req.getCookies())
//                    .forEach(m->{
//                        System.out.println(m.getName()+":"+m.getValue()+"--"+m.getMaxAge());
//                    });
    }

    @Override
    final protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
