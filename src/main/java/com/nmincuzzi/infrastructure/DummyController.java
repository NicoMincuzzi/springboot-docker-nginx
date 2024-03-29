package com.nmincuzzi.infrastructure;

import com.nmincuzzi.domain.Dummy;
import com.nmincuzzi.usecase.GetDummy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/dummy", produces = APPLICATION_JSON_VALUE)
public class DummyController {
    private final GetDummy getDummy;

    DummyController(GetDummy getDummy) {
        this.getDummy = getDummy;
    }

    @GetMapping("/success")
    public ResponseEntity<Dummy> dummySuccess() {
        return ResponseEntity.status(OK).body(getDummy.apply());
    }

    @GetMapping("/badrequest")
    public void dummyBadRequest() {
        try {
            getDummy.throwDummyBadRequest();
        } catch (BadRequestException e) {
            throw new ResponseStatusException(BAD_REQUEST, "Dummy Bad Request", e);
        }
    }

    @GetMapping("/notfound/{id}")
    public Dummy dummyNotFoundByNameFilter(@PathVariable String id, @RequestParam String name) {
        return getDummy.applyBy(id, name);
    }

    @GetMapping(value = {"/optional", "/optional/{id}"}, produces = APPLICATION_JSON_VALUE)
    public Dummy getFooByOptionalId(@PathVariable(required = false) String id) {
        return getDummy.applyBy(id);
    }
}