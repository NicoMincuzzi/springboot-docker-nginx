package com.nmincuzzi.model;

import lombok.Data;

@Data(staticConstructor = "of")
public class DummyModel {
    private String id;
    private Integer code;
    private String message;
}
