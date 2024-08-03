package com.dihari.majduri.DihariMajduri.mobile.common;

public enum StatusCode {
    AUTHENTICATE(1001),
    NOT_AUTHENTICATE(1000),
    MOBILE_NUMBER_NOT_EXISTS(1002),
    MOBILE_NUMBER_EXISTS(1003),
    ID_NOT_EXISTS(1004),
    ID_EXISTS(1005),
	INTERNAL_SERVER_ERROR(1006),
	OWNER_NOT_EXISTS(1007),
	LABOUR_NOT_EXISTS(1008);

    private final int code;

    StatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

