package com.cms.wechat;

import com.cms.infobeans.QueryRoomParam;
import com.cms.infobeans.wechat.WCText;
import com.cms.sqltools.SqlSessionManagement;
import com.cms.wechat.event.WCTextEvent;
import org.apache.ibatis.exceptions.PersistenceException;

import java.util.*;

import static com.cms.sqltools.SqlKey.CURRENT_TERM;
import static com.cms.sqltools.SqlKey.QUERY_ROOM;

public class WCSelectRoom implements WCTextEvent{
    private static String match;
    private static List<Character> dayList= Arrays.asList('前','昨','今','明','后');
    private static Long startDay;
    private static SqlSessionManagement<Date> sqls;

    static{
        sqls=SqlSessionManagement.getInstance();
        String builds[]={"知行","明德","弘毅","天佑","弘毅","艺悦","精工"};
        StringJoiner asd=new StringJoiner(")|(","查询((前)|(昨)|(今)|(明)|(后))天((全部)|(","))楼((占用)|(空闲))课室");
        for(String str:builds){
            asd.add(str);
        }
        match=asd.toString();
        startDay=sqls.customSqlSession(sqlSession -> sqlSession.selectOne(CURRENT_TERM)).getTime();
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
        QueryRoomParam queryRoomParam=new QueryRoomParam();
        queryRoomParam.setBuildingName(build);
        queryRoomParam.setFindUsing("占用".equals(text.substring(7,9)));

        Long staD=System.currentTimeMillis()+(day*24*60*60*1000)-startDay;
        queryRoomParam.setQueryClassWeek((int)(staD/(1000*60*60*24))/7+1);
        queryRoomParam.setQueryWeek((int)(staD/(1000*60*60*24))%7);

        StringBuilder reText=new StringBuilder();
        try {
            sqls.customSqlSession(sqlSession ->{
                List<Map> resultList=sqlSession.selectList(QUERY_ROOM, queryRoomParam);
                reText.append("查询结果如下").append('\n')
                    .append("课室 时间");
                for (Map rMap:resultList) {
                    reText.append(rMap.get("RoomName")).append(' ').append(rMap.get("ClassTime")).append('\n');
                }
                return null;
            });
        }catch (PersistenceException e){
            reText.append(e.getCause().getMessage());
        }
        weChatIO.replyText(reText.toString());
    }
}
