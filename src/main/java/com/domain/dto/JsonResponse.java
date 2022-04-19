package com.domain.dto;

import java.io.Serializable;

public class JsonResponse<T> implements Serializable {
    private int statusCode;
    private String status;
    private T data;

    public JsonResponse(int statusCode, String status, T data) {
        this.statusCode = statusCode;
        this.status = status;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
