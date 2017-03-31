package com.balancer.service.model.json.response;


public class ResponseMessage {
    private String machine;
    private String message;
    private Status status = Status.SUCCESS;

    public ResponseMessage() {
    }

    public ResponseMessage(String machine, String message, Status status) {
        this.machine = machine;
        this.message = message;
        this.status = status;
    }

    public ResponseMessage(String machine, String message) {
        this.machine = machine;
        this.message = message;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public enum Status {
        SUCCESS,
        FAILURE
    }
}
