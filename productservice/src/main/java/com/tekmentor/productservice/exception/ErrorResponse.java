package com.tekmentor.productservice.exception;

public class ErrorResponse {
    private int status;
    private String detail;
    private String instance;
    private String title;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getInstance() {
        return instance;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
