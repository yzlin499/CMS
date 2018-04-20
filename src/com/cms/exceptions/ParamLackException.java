package com.cms.exceptions;

import com.cms.frameclass.StateCode;

public class ParamLackException extends CMSException {
    public ParamLackException(String message){
        super(StateCode.PARAM_LACK,message);
    }
}
