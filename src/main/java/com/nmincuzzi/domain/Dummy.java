package com.nmincuzzi.domain;

import java.util.Objects;

public class Dummy {
    private final String id;
    private final String name;
    private final Integer code;
    private final String message;

    public Dummy(String id, String name, Integer code, String message) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dummy that = (Dummy) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
