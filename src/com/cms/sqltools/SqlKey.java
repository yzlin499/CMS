package com.cms.sqltools;

public interface SqlKey {
    String REGISTER     ="com.cms.sqltools.DisposeUser.Register";
    String LOGIN_USER   ="com.cms.sqltools.DisposeUser.LoginUser";
    String SELECT_PERSON="com.cms.sqltools.DisposeUser.SelectPerson";

    String CREATE_COURSE="com.cms.sqltools.DisposeCourse.CreateCourse";
    String FILTER_COURSE="com.cms.sqltools.DisposeCourse.FilterCourse";
    String UPDATE_COURSE="com.cms.sqltools.DisposeCourse.UpdateCourse";
    String DELETE_COURSE="com.cms.sqltools.DisposeCourse.DeleteCourse";

    String SELECT_ROOM ="com.cms.sqltools.DisposeBuildRoom.SelectRoom";
    String CREATE_ROOM ="com.cms.sqltools.DisposeBuildRoom.CreateRoom";
    String UPDATE_ROOM ="com.cms.sqltools.DisposeBuildRoom.UpdateRoom";
    String DELETE_ROOM ="com.cms.sqltools.DisposeBuildRoom.DeleteRoom";
    String FILTER_ROOM ="com.cms.sqltools.DisposeBuildRoom.FilterRoom";
    String  QUERY_ROOM ="com.cms.sqltools.DisposeBuildRoom.QueryRoom";

    String SELECT_TERM ="com.cms.sqltools.DisposeTerm.SelectTerm";
    String CREATE_TERM ="com.cms.sqltools.DisposeTerm.CreateTerm";
    String UPDATE_TERM ="com.cms.sqltools.DisposeTerm.UpdateTerm";
    String DELETE_TERM ="com.cms.sqltools.DisposeTerm.DeleteTerm";
    String CURRENT_TERM="com.cms.sqltools.DisposeTerm.CurrentTerm";
}
