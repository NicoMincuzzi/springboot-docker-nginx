package com.nmincuzzi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No dummy found")
public class DummyNotFoundException extends RuntimeException{
}
