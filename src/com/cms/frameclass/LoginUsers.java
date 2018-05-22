package com.cms.frameclass;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class LoginUsers {
    private static class InstanceClass {
        private static final LoginUsers instance=new LoginUsers();
    }
    private LoginUsers(){}
    public static LoginUsers getInstance(){
        return InstanceClass.instance;
    }

    private Map<String,HttpSession> webLoginMap=new HashMap<>();
    private Map<String,HttpSession> phoneLoginMap=new HashMap<>();
    private Map<String,HttpSession> clientLoginMap=new HashMap<>();

    public void webLogin(String user,HttpSession httpSession){
        newLogin(user,httpSession,webLoginMap);
    }

    public void phoneLogin(String user,HttpSession httpSession){
        newLogin(user,httpSession,phoneLoginMap);
    }

    public void clientLogin(String user,HttpSession httpSession){
        newLogin(user,httpSession,clientLoginMap);
    }

    private void newLogin(String user,HttpSession httpSession,Map<String,HttpSession> loginMap){
        httpSession.setMaxInactiveInterval(36000);
        httpSession.setAttribute("loginState", true);
        httpSession.setAttribute("userName",user);
        HttpSession httpSession2=loginMap.put(user,httpSession);
        if(httpSession2!=null && (!httpSession2.equals(httpSession))){
            try {
                httpSession2.setAttribute("loginState", false);
                httpSession2.invalidate();
            }catch (IllegalStateException e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }
}
