package com.cms.exceptions;

import com.cms.frameclass.StateCode;

public class ForbiddenException extends CMSException {
    public ForbiddenException(String message){
        super(StateCode.FORBIDDEN,message);
    }
}
