package com.nmincuzzi.controller;

import com.nmincuzzi.model.DummyModel;
import com.nmincuzzi.service.DummyService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/dummy")
class DummyController {
    private final DummyService dummyService;

    DummyController(DummyService dummyService) {
        this.dummyService = dummyService;
    }

    @GetMapping(value = "/success", produces = APPLICATION_JSON_VALUE)
    public DummyModel dummySuccess() {
        return dummyService.retrieveDummyInfo();
    }

    @GetMapping("/badrequest")
    public void dummyBadRequest() {
        try {
            dummyService.throwDummyBadRequest();
        } catch (BadRequestException e) {
            throw new ResponseStatusException(BAD_REQUEST, "Dummy Bad Request", e);
        }
    }

    @GetMapping("/notfound/{id}")
    public DummyModel dummyNotFoundByNameFilter(@PathVariable String id, @RequestParam String name) {
        return dummyService.retrieveDummyInfoByIdAndName(id, name);
    }

    @GetMapping(value = {"/optional", "/optional/{id}"}, produces = APPLICATION_JSON_VALUE)
    public DummyModel getFooByOptionalId(@PathVariable(required = false) String id) {
        return dummyService.retrieveDummyInfoById(id);
    }
}