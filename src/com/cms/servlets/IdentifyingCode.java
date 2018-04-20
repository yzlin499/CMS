package com.cms.servlets;


import com.cms.tools.Tools;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "IdentifyingCode",value = "/identifyingCode")
public class IdentifyingCode extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String temp[]=Tools.createIdentifyingCode();
        HttpSession httpSession=req.getSession();
        httpSession.setAttribute("IdentifyingCode",Tools.MD5(temp[0].toLowerCase()));
        resp.getWriter().print(temp[1]);
    }
}
