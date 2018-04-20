package com.cms.infobeans;

import com.cms.infobeans.beaninterface.BasicInterface;
import com.cms.infobeans.beaninterface.InstanceByMap;
import com.cms.infobeans.beaninterface.MapKey;
import net.sf.json.JSONObject;

public class Course implements BasicInterface {
    @MapKey("CName")
    private String CourseName;
    @MapKey("CClass")
    private String ClassName;
    @MapKey("CWeek")
    private int Week=-1;
    @MapKey("CClWeek")
    private int ClassWeek=-1;
    @MapKey("CClTime")
    private int ClassTime=-1;
    @MapKey("CTeacher")
    private String Teacher;
    @MapKey("RName")
    private String Room;
    @MapKey("CStuCount")
    private int StuCount=0;
    @MapKey("TID")
    private int CurTermID=-1;
    @MapKey("CID")
    private int CourseID=-1;

    public Course(){}

    public Course(JSONObject jsonObject){
        InstanceByMap.createInstance(jsonObject,this);
    }

    @Override
    public boolean isNotNull(){
        return (CourseName!=null && ClassName!=null && Week!=-1 && ClassWeek!=-1 && ClassTime!=-1
                && Teacher!=null && Room!=null && StuCount!=-1 && CurTermID!=-1 && CourseID!=-1);
    }

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int courseID) {
        CourseID = courseID;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public int getWeek() {
        return Week;
    }

    public void setWeek(int week) {
        Week = week;
    }

    public int getClassWeek() {
        return ClassWeek;
    }

    public void setClassWeek(int classWeek) {
        ClassWeek = classWeek;
    }

    public int getClassTime() {
        return ClassTime;
    }

    public void setClassTime(int classTime) {
        ClassTime = classTime;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        Teacher = teacher;
    }

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public int getStuCount() {
        return StuCount;
    }

    public void setStuCount(int stuCount) {
        StuCount = stuCount;
    }

    public int getCurTermID() {
        return CurTermID;
    }

    public void setCurTermID(int curTermID) {
        CurTermID = curTermID;
    }

}
