package com.cms.servlets;

import com.cms.exceptions.CMSException;
import com.cms.exceptions.DBException;
import com.cms.exceptions.ParamLackException;
import com.cms.exceptions.UnknownFailException;
import com.cms.frameclass.JSONAPIServlet;
import com.cms.frameclass.LoginUsers;
import com.cms.frameclass.StateCode;
import com.cms.infobeans.UserInfo;
import com.cms.sqltools.SqlKey;
import com.cms.sqltools.SqlSessionManagement;
import com.cms.tools.Tools;
import net.sf.json.JSONObject;
import org.apache.ibatis.exceptions.PersistenceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name="LoginUser",value = "/loginUser")
public class LoginUser extends JSONAPIServlet {
    SqlSessionManagement<UserInfo> sqlSM=SqlSessionManagement.getInstance();
    LoginUsers loginUsers=LoginUsers.getInstance();

    @Override
    protected JSONObject APIFunction(JSONObject data) throws ServletException, IOException, CMSException {
        JSONObject resultJSON=new JSONObject();
        String UserName=data.containsKey("UserName")?data.getString("UserName"):null;
        String Password=data.containsKey("Password")?data.getString("Password"):null;
        String loginWay=data.containsKey("LoginWay")?data.getString("LoginWay").toUpperCase():"WEB";
        String sign=data.containsKey("sign")?data.getString("sign"):null;
        if(UserName==null || Password==null){
            throw new ParamLackException("没有密码或者登录名");
        }else if(!checkSign(UserName,Password,sign)){
            throw new ParamLackException("签名失败");
        }else{
            UserInfo userInfo=new UserInfo();
            userInfo.setUserName(UserName);
            userInfo.setPassWord(Tools.MD5(Password.toLowerCase()));
            try {
                UserInfo ui = sqlSM.customSqlSession(sqls ->sqls.selectOne(SqlKey.LOGIN_USER, userInfo));
                if (ui!=null){
                    ui.setUserName(UserName);
                    resultJSON.put("state", StateCode.COMPLETE);
                    resultJSON.put("message", "登录成功");
                    resultJSON.put("data", ui.toJSON());
                    switch (loginWay){
                        case "PHONE":loginUsers.phoneLogin(UserName,getHttpSession());break;
                        case "CLIENT":loginUsers.clientLogin(UserName,getHttpSession());break;
                        default:loginUsers.webLogin(UserName,getHttpSession());break;
                    }
                }
            }catch (PersistenceException e){
                throw new DBException(e.getCause().getMessage());
            }
        }
        return resultJSON;
    }


    private boolean checkSign(String UserName,String Password,String sign) {
        if(sign==null){
            return false;
        }
        String param="UserName="+UserName+"&Password="+Password+"&p=cms";
        param= Tools.MD5(param).substring(12,20);
        return param.equals(sign);
    }

}
