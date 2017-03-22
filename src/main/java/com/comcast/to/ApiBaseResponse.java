package com.comcast.to;

/**
 * Created by kotabek on 3/22/17.
 */
public class ApiBaseResponse {
    private String status;
    private String message;

    public ApiBaseResponse() {
    }

    public ApiBaseResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
