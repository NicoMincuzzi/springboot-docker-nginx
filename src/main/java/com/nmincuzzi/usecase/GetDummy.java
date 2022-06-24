package com.nmincuzzi.usecase;

import com.nmincuzzi.domain.Dummy;
import com.nmincuzzi.infrastructure.BadRequestException;
import com.nmincuzzi.infrastructure.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpStatus.OK;

@Service
public class GetDummy {
    private final Logger logger = LoggerFactory.getLogger(GetDummy.class);

    public Dummy apply() {
        logger.info("Retrieve dummy info by Service.");
        return new Dummy(randomUUID().toString(), "John", OK.value(), OK.name());
    }

    public Dummy applyBy(String id) {
        if (id == null || id.isEmpty()) {
            id = randomUUID().toString();
        }
        return new Dummy(id, "John", OK.value(), OK.name());
    }

    public Dummy applyBy(String id, String name) {
        if (id == null || id.isEmpty()) {
            id = randomUUID().toString();
        }
        if (!name.equals("Jack")) {
            throw new NotFoundException("The user ".concat(name).concat(" is not presented!"));
        }

        return new Dummy(id, "Jack", OK.value(), OK.name());
    }

    public void throwDummyBadRequest() throws BadRequestException {
        throw new BadRequestException();
    }
}
