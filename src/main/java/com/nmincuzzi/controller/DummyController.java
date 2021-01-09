package com.nmincuzzi.controller;

import com.nmincuzzi.model.DummyModel;
import com.nmincuzzi.service.DummyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy")
class DummyController {

    private final DummyService dummyService;

    DummyController(DummyService dummyService) {
        this.dummyService = dummyService;
    }

    @GetMapping("")
    public DummyModel getResult() {
        return this.dummyService.retrieveDummyInfo();
    }
}