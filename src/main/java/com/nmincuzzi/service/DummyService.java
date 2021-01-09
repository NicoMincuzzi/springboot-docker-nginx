package com.nmincuzzi.service;

import com.nmincuzzi.exception.DummyBadRequestException;
import com.nmincuzzi.model.DummyModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DummyService {

    public DummyModel retrieveDummyInfo() {
        DummyModel dummyModel = DummyModel.of();
        dummyModel.setId(UUID.randomUUID().toString());
        dummyModel.setCode(HttpStatus.OK.value());
        dummyModel.setMessage(HttpStatus.OK.name());
        return dummyModel;
    }

    public DummyModel retrieveDummyInfoById(String id) {
        DummyModel dummyModel = DummyModel.of();
        if(id != null && !id.isEmpty()){
            dummyModel.setId(id);
        }else{
            dummyModel.setId(UUID.randomUUID().toString());
        }
        dummyModel.setCode(HttpStatus.OK.value());
        dummyModel.setMessage(HttpStatus.OK.name());
        return dummyModel;
    }

    public void throwDummyBadRequest() throws DummyBadRequestException {
        throw new DummyBadRequestException();
    }
}
