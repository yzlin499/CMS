package com.cms.servlets;

import com.cms.frameclass.CacheState;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ResetCache",value = "/resetCache")
public class ResetCache extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if("123456789".equals(request.getParameter("token"))){
            CacheState.getInstance().resetAllCache();
            response.getWriter().println("缓存更新成功");
        }else{
            response.getWriter().println("缓存更新失败");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if("123456789".equals(request.getParameter("token"))){
            CacheState.getInstance().resetAllCache();
            response.getWriter().println("缓存更新成功");
        }else{
            response.getWriter().println("缓存更新失败");
        }
    }
}
