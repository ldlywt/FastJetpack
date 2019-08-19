package com.fastaac.base.base;

import java.io.Serializable;

/**
 * author : wutao
 * e-mail : wutao@himango.cn
 * time   : 2019/07/30
 * desc   :
 * version: 1.0
 */
public class BaseResult<T> implements Serializable {

    public static final int SUCCESS_CODE = 0;
    private static final long serialVersionUID = 5213230387175987834L;

    private int errorCode;
    private String errorMsg;
    private T data;

    @Override
    public String toString() {
        return "LzyResponse{\n" +//
                "\terrorCode=" + errorCode + "\n" +//
                "\terrorMsg='" + errorMsg + "\'\n" +//
                "\tdata=" + data + "\n" +//
                '}';
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg == null ? "" : errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}