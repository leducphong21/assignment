package com.io.api.dto.response;

public class ResponseDTO {
    protected Object data;
    protected String status;
    protected String message;

    public ResponseDTO() {
    }
    public ResponseDTO(String status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
    public ResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
