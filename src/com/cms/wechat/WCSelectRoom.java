package com.cms.wechat;

import com.cms.infobeans.FilterRoomParam;
import com.cms.infobeans.wechat.WCText;
import com.cms.wechat.event.WCTextEvent;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class WCSelectRoom implements WCTextEvent{
    private static String match;
    private static List<Character> dayList= Arrays.asList('前','昨','今','明','后');
    static{
        String builds[]={"知行","明德","弘毅","天佑","弘毅","艺悦","精工"};
        StringJoiner asd=new StringJoiner(")|(","查询((前)|(昨)|(今)|(明)|(后))天((全部)|(","))楼((占用)|(空闲))课室");
        for(String str:builds){
            asd.add(str);
        }
        match=asd.toString();
    }

    @Override
    public boolean filter(WeChatIO<WCText> wcInfo) {
        return wcInfo.getInfo().getContent().matches(match);
    }

    @Override
    public void disposeMag(WeChatIO<WCText> weChatIO) {
        String text=weChatIO.getInfo().getContent();
        String build=text.substring(4,7);
        build="全部楼".equals(build)?null:build;
        int day=dayList.indexOf(text.charAt(2))-2;
        FilterRoomParam filterRoomParam=new FilterRoomParam();
        filterRoomParam.setBuildingName(build);
        filterRoomParam.setFindUsing("占用".equals(text.substring(7,9)));
    }

//    public int calculateWeek(){
//
//    }

}
