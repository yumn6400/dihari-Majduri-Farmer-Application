package com.dihari.majduri.DihariMajduri.mobile.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper {
	private boolean success;
    private String message;
    private int internalCode;
    private Object data;

    public ResponseWrapper(boolean success , Object data) {
    	this.success=success;
        this.data = data;
    }
    
    public ResponseWrapper(boolean success , Object data, String message) {
    	this.success=success;
        this.data = data;
        this.message=message;
    }
    public ResponseWrapper(boolean success , int internalCode, String message) {
    	this.success=success;
        this.internalCode=internalCode;
        this.message = message;
    }
    public ResponseWrapper(boolean success , int internalCode) {
    	this.success=success;
        this.internalCode=internalCode;
    }

}
