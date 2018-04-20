package com.cms.servlets;

import com.cms.frameclass.StateCode;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.cms.tools.Tools.cnToUnicode;

@WebServlet(name = "LogoutUser",value = "/logoutUser")
public class LogoutUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession=req.getSession();
        httpSession.setAttribute("loginState",false);
        httpSession.invalidate();
        JSONObject result=new JSONObject();
        result.put("state", StateCode.COMPLETE);
        result.put("message","删除成功");
        resp.setCharacterEncoding("Utf-8");
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().print(cnToUnicode(result.toString()));
    }
}
