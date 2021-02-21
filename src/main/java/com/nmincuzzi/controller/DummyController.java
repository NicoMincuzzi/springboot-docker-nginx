package com.nmincuzzi.controller;

import com.nmincuzzi.exception.DummyBadRequestException;
import com.nmincuzzi.exception.DummyNotFoundException;
import com.nmincuzzi.model.DummyModel;
import com.nmincuzzi.service.DummyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/dummy")
class DummyController {

    private final DummyService dummyService;

    DummyController(DummyService dummyService) {
        this.dummyService = dummyService;
    }

    @GetMapping(value = "/success", produces = MediaType.APPLICATION_JSON_VALUE)
    public DummyModel dummySuccess() {
        return this.dummyService.retrieveDummyInfo();
    }

    @GetMapping("/badrequest")
    public void dummyBadRequest() {
        try {
            this.dummyService.throwDummyBadRequest();
        } catch (DummyBadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dummy Bad Request", e);
        }
    }

    @GetMapping("/notfound/{id}")
    public void dummyNotFound(@PathVariable String id) {
        DummyModel dummyModel = this.dummyService.retrieveDummyInfo();
        if (!id.equals(dummyModel.getId())) {
            throw new DummyNotFoundException();
        }
    }

    @GetMapping(value = {"/optional", "/optional/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public DummyModel getFooByOptionalId(@PathVariable(required = false) String id) {
        return this.dummyService.retrieveDummyInfoById(id);
    }

}