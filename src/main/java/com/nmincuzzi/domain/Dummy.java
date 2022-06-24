package com.nmincuzzi.domain;

public class Dummy {
    private String id;
    private String name;
    private Integer code;
    private String message;

    public Dummy(String id, String name, Integer code, String message) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
