package com.nmincuzzi.service;

import com.nmincuzzi.model.DummyModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DummyService {

    public DummyModel retrieveDummyInfo() {
        DummyModel dummyModel = DummyModel.of();
        dummyModel.setCode(HttpStatus.OK.value());
        dummyModel.setMessage(HttpStatus.OK.name());
        return dummyModel;
    }
}
