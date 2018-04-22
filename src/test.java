import com.cms.infobeans.QueryRoomParam;
import com.cms.infobeans.UserInfo;
import com.cms.sqltools.SqlSessionManagement;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.cms.sqltools.SqlKey.QUERY_ROOM;


public class test {
    int a=123;

    public int getA() {
        return a;
    }

    @Override
    public String toString() {
        return "qqqqqq";
    }

    public static void main(String[]args){
        UserInfo userInfo=new UserInfo();
        userInfo.setSex(true);
        System.out.println(userInfo.toJSON());
    }
}
