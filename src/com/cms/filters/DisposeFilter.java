package com.cms.filters;

import com.cms.frameclass.StateCode;
import net.sf.json.JSONObject;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.cms.tools.Tools.cnToUnicode;

@WebFilter(urlPatterns = "/selectRoom")
public class DisposeFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session=request.getSession();
        session.setMaxInactiveInterval(36000);
        Boolean loginState=(Boolean) session.getAttribute("loginState");
        if(new Boolean(true).equals(loginState)){
            chain.doFilter(request,response);
        }else{
            JSONObject result=new JSONObject();
            result.put("state",StateCode.FORBIDDEN);
            result.put("message","权限不足");
            response.setCharacterEncoding("Utf-8");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(cnToUnicode(result.toString()));
        }
    }
}
