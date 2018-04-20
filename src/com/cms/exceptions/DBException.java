package com.cms.exceptions;

import com.cms.frameclass.StateCode;

public class DBException extends CMSException{
    public DBException(String message){
        super(StateCode.DB_ERROR,message);
    }
}
