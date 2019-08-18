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

    public int result;
    public String message;
    public T data;
    public String filePath;

    @Override
    public String toString() {
        return "LzyResponse{\n" +//
                "\tresult=" + result + "\n" +//
                "\tmessage='" + message + "\'\n" +//
                "\tdata=" + data + "\n" +//
                '}';
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getFilePath() {
        return filePath == null ? "" : filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}