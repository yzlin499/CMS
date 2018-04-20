import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.lang.model.util.Elements;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {
    public static void main(String[]args){
        Map<String ,String> j=new HashMap<>();
        j.put("asd","asdasd");
        j.put("aqwesd","asdasd");
        j.put("asqwed","asdasd");

        System.out.println(JSONObject.fromObject(j));
    }
}
