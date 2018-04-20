package com.cms.exceptions;

import com.cms.frameclass.StateCode;

public class NoJSONException extends CMSException {
    public NoJSONException(String message){
        super(StateCode.NO_JSON,message);
    }
}
