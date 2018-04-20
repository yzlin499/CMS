package com.cms.infobeans;

import com.cms.infobeans.beaninterface.BasicInterface;
import com.cms.infobeans.beaninterface.InstanceByMap;
import com.cms.infobeans.beaninterface.MapKey;
import net.sf.json.JSONObject;

public class Room implements BasicInterface{
    @MapKey("RID")
    private int RoomID=-1;
    @MapKey("RName")
    private String RoomName;
    @MapKey("BName")
    private String BuildingName;
    @MapKey("RMaxCount")
    private int MaxStudentCount=-1;
    @MapKey("RUseFor")
    private String UseFor;

    public Room(){}

    public Room(JSONObject jsonObject){
        InstanceByMap.createInstance(jsonObject,this);
    }

    @Override
    public boolean isNotNull(){
        if(RoomName!=null && BuildingName!=null && RoomID!=-1 && MaxStudentCount!=-1 && UseFor !=null){
            return true;
        }else{
            return false;
        }
    }

    public int getRoomID() {
        return RoomID;
    }

    public void setRoomID(int roomID) {
        RoomID = roomID;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }

    public int getMaxStudentCount() {
        return MaxStudentCount;
    }

    public void setMaxStudentCount(int maxStudentCount) {
        MaxStudentCount = maxStudentCount;
    }

    public String getUseFor() {
        return UseFor;
    }

    public void setUseFor(String useFor) {
        UseFor = useFor;
    }


}
