package com.cms.infobeans;

import com.cms.infobeans.beaninterface.BasicInterface;
import com.cms.infobeans.beaninterface.InstanceByMap;
import com.cms.infobeans.beaninterface.MapKey;
import net.sf.json.JSONObject;


public class UserInfo implements BasicInterface{
    @MapKey("UserName")
    private String UserName;
    @MapKey("password")
    private String PassWord;
    @MapKey("name")
    private String Name;
    @MapKey("mail")
    private String EMail;
    @MapKey("theClass")
    private String ClassName;
    @MapKey("phone")
    private String TelPhone;
    @MapKey("sex")
    private boolean sex;
    private int PersonID;
    private int PersonCtrlLevel;

    public UserInfo(){}

    public UserInfo(JSONObject jo){
        InstanceByMap.createInstance(jo,this);
    }

    public int getPersonID() {
        return PersonID;
    }

    public void setPersonID(int personID) {
        PersonID = personID;
    }

    public int getPersonCtrlLevel() {
        return PersonCtrlLevel;
    }

    public void setPersonCtrlLevel(int personCtrlLevel) {
        PersonCtrlLevel = personCtrlLevel;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getTelPhone() {
        return TelPhone;
    }

    public void setTelPhone(String telPhone) {
        TelPhone = telPhone;
    }

}
