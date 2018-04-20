package com.cms.exceptions;

import com.cms.frameclass.StateCode;

public class UnknownFailException extends CMSException {
    public UnknownFailException(String message){
        super(StateCode.UNKNOWN_FAIL,message);
    }
}
