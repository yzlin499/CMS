package com.cms.servlets;

import com.cms.exceptions.CMSException;
import com.cms.exceptions.DBException;
import com.cms.exceptions.ParamLackException;
import com.cms.frameclass.CacheState;
import com.cms.frameclass.JSONAPIServlet;
import com.cms.frameclass.StateCode;
import com.cms.infobeans.UserInfo;
import com.cms.sqltools.SqlSessionManagement;
import com.cms.tools.Tools;
import net.sf.json.JSONObject;
import org.apache.ibatis.exceptions.PersistenceException;

import javax.servlet.annotation.WebServlet;

import static com.cms.sqltools.SqlKey.REGISTER;

@WebServlet(name="RegisterServlet",value="/registerServlet")
public class RegisterServlet extends JSONAPIServlet {
    private static CacheState cacheState=CacheState.getInstance();
    private SqlSessionManagement<Boolean> sqlSM=SqlSessionManagement.getInstance();
    protected JSONObject APIFunction(JSONObject data) throws CMSException {
        UserInfo userInfo=new UserInfo(data);
        if(userInfo.getUserName()==null&& !userInfo.getUserName().matches("^[a-zA-Z0-9]{3,16}$")){
            throw new ParamLackException("非法用户名");
        }else if(userInfo.getEMail()==null&& !userInfo.getEMail().matches("^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$")){
            throw new ParamLackException("非法邮箱");
        }else if(userInfo.getPassWord()==null&& !userInfo.getPassWord().matches("^[a-z0-9]{32}$")){
            throw new ParamLackException("非法密码");
        }else if(userInfo.getName()==null && !userInfo.getName().matches("^[\u4E00-\u9FA5]{2,5}")){
            throw new ParamLackException("非法名字");
        }else if(userInfo.getTelPhone()==null && !userInfo.getTelPhone().matches("^1[34578]\\d{9}$")) {
            throw new ParamLackException("非法手机");
        }else if(!data.containsKey("IdentifyingCode")) {
            throw new ParamLackException("验证码不存在");
        }else if((!data.getString("IdentifyingCode").equals(getHttpSession().getAttribute("IdentifyingCode")))){
            throw new ParamLackException("验证码错误");
        }else if(userInfo.getClassName()==null){
            throw new ParamLackException("非法班级");
        }else{
            try {
                userInfo.setPassWord(Tools.MD5(userInfo.getPassWord()));
                sqlSM.customSqlSession(sqls->{
                    sqls.selectOne(REGISTER, userInfo);
                    return true;
                });
            }catch (PersistenceException e){
                throw new DBException(e.getCause().getMessage());
            }
        }
        JSONObject result=new JSONObject();
        result.put("state", StateCode.COMPLETE);
        result.put("message","注册成功");
        cacheState.dataUpdate(SelectPerson.class);
        return result;
    }
}
