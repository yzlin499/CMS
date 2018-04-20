import com.cms.infobeans.QueryRoomParam;
import com.cms.sqltools.SqlSessionManagement;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

import static com.cms.sqltools.SqlKey.QUERY_ROOM;


public class test {
    public static void main(String[]args){
        SqlSessionManagement<List<Map>> asd=SqlSessionManagement.getInstance();
        JSONArray dataNode=new JSONArray();
        QueryRoomParam dataBean=new QueryRoomParam();
        dataBean.setBuildingName("知行楼");
        dataBean.setFindUsing(true);
        dataBean.setQueryWeek(1);
        dataBean.setQueryClassWeek(1);
        dataBean.setTermID(0);
        dataNode.addAll(
                    asd.customSqlSession(sqlSession ->sqlSession.selectList(QUERY_ROOM, dataBean))
                            .stream()
                            .map(JSONObject::fromObject).collect(Collectors.toList())
            );
        System.out.println(dataNode);





//        Calendar calendar=Calendar.getInstance();
//        calendar.set(2018,2,05,16,12,12);
//        Long nowTime=calendar.getTimeInMillis();
//        Long staD=nowTime-asd.customSqlSession(sqlSession -> sqlSession.selectOne(CURRENT_TERM)).getTime();
//
//        int week=(int)(staD/(1000*60*60*24))/7+1;
//        int day=(int)(staD/(1000*60*60*24))%7;
//        System.out.println(week);
//        System.out.println(day);
    }
}
