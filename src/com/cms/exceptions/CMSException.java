package com.cms.exceptions;

public class CMSException extends Exception {
    private int state;

    protected CMSException(int state,String message){
        super(message);
        this.state=state;
    }

    public int getState() {
        return state;
    }

}
