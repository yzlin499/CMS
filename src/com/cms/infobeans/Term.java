package com.cms.infobeans;

import com.cms.infobeans.beaninterface.BasicInterface;
import com.cms.infobeans.beaninterface.InstanceByMap;
import com.cms.infobeans.beaninterface.MapKey;
import net.sf.json.JSONObject;

import java.util.Date;

public class Term implements BasicInterface {
    @MapKey("TID")
    private int TermID=-1;
    @MapKey("TName")
    private String TermName;
    @MapKey("TStartDate")
    private Date StartDate;

    public Term(){}

    public Term(JSONObject jo){
        InstanceByMap.createInstance(jo,this);
    }

    @Override
    public boolean isNotNull(){
        return TermID!=-1 && TermName!=null && StartDate!=null;
    }

    public int getTermID() {
        return TermID;
    }

    public void setTermID(int termID) {
        TermID = termID;
    }

    public String getTermName() {
        return TermName;
    }

    public void setTermName(String termName) {
        TermName = termName;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

}
