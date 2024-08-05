package com.dihari.majduri.DihariMajduri.mobile.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper<T> {
    private boolean success;
    private T data;
    private int internalCode;
    private String message;

    public ResponseWrapper(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

}
