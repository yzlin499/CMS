package com.cms.exceptions;

import com.cms.frameclass.StateCode;

public class DBBoonException extends CMSException {
    public DBBoonException(String message) {
        super(StateCode.DB_BOON, message);
    }
}
