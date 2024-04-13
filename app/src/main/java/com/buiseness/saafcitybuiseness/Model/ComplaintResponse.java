package com.buiseness.saafcitybuiseness.Model;

import java.io.InputStream;

public class ComplaintResponse {

    private boolean success;
    private String status;
    private String message;
    private InputStream imageData;
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
    public InputStream byteStream() {
        return imageData;
    }


}
