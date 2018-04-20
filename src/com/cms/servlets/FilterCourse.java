package com.cms.servlets;

import com.cms.exceptions.CMSException;
import com.cms.exceptions.DBException;
import com.cms.frameclass.FilterClass;
import com.cms.infobeans.Course;
import net.sf.json.JSONArray;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.annotation.WebServlet;

import static com.cms.sqltools.SqlKey.FILTER_COURSE;

@WebServlet(name = "FilterCourse ",value = "/filterCourse")
public class FilterCourse extends FilterClass<Course> {
    @Override
    protected void responseData(Course req,SqlSession sqls, JSONArray resultData) throws CMSException{
        try {
            sqls.selectList(FILTER_COURSE,req).forEach(data ->resultData.add(((Course)data).toJSON()));
        }catch (PersistenceException e){
            throw new DBException(e.getCause().getMessage());
        }
    }
}
