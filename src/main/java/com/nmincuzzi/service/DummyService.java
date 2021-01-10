package com.nmincuzzi.service;

import com.nmincuzzi.exception.DummyBadRequestException;
import com.nmincuzzi.model.DummyModel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class DummyService {

    Logger logger = LoggerFactory.getLogger(DummyService.class);

    public DummyModel retrieveDummyInfo() {
        logger.info("Retrieve dummy info by Service.");
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
