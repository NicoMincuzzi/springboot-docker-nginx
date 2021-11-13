package com.nmincuzzi.service;

import com.nmincuzzi.controller.BadRequestException;
import com.nmincuzzi.controller.NotFoundException;
import com.nmincuzzi.model.DummyModel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Service
public class DummyService {

    Logger logger = LoggerFactory.getLogger(DummyService.class);

    public DummyModel retrieveDummyInfo() {
        logger.info("Retrieve dummy info by Service.");
        return new DummyModel(randomUUID().toString(), "John", OK.value(), OK.name());
    }

    public DummyModel retrieveDummyInfoById(String id) {
        if (id == null || id.isEmpty()) {
            id = randomUUID().toString();
        }
        return new DummyModel(id, "John", OK.value(), OK.name());
    }

    public DummyModel retrieveDummyInfoByIdAndName(String id, String name) {
        if (id == null || id.isEmpty()) {
            id = randomUUID().toString();
        }
        if (!name.equals("Jack")) {
            throw new NotFoundException("The user ".concat(name).concat(" is not presented!"));
        }

        return new DummyModel(id, "Jack", OK.value(), OK.name());
    }

    public void throwDummyBadRequest() throws BadRequestException {
        throw new BadRequestException();
    }
}
