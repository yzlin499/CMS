package com.cms.frameclass;

import com.cms.exceptions.CMSException;
import com.cms.tools.Tools;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static com.cms.tools.Tools.cnToUnicode;

public abstract class JSONAPIServlet extends HttpServlet {
    private JSONObject responseJSON;
    private HttpSession httpSession;
    @Override
    final protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        httpSession=request.getSession();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        JSONObject data= Tools.dataToJSONObject(request);
        if(data==null){
            responseJSON=new JSONObject();
            responseJSON.put("state", StateCode.NO_JSON);
            responseJSON.put("message","数据传输错误");
        }else {
            try {
                responseJSON=APIFunction(data);
            } catch (CMSException e) {
                responseJSON=new JSONObject();
                responseJSON.put("state", e.getState());
                responseJSON.put("message",e.getMessage());
            }
        }
        PrintWriter out=response.getWriter();

        out.write(cnToUnicode(responseJSON.toString()));
        out.flush();
        out.close();
        httpSession=null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        responseJSON=new JSONObject();
        responseJSON.put("state",StateCode.FORBIDDEN);
        responseJSON.put("message","不支持get方式");
        response.getWriter().print(responseJSON);
    }

    protected abstract JSONObject APIFunction(JSONObject data) throws ServletException, IOException,CMSException;

    final protected HttpSession getHttpSession(){
        return httpSession;
    }
}
