package io.github.tomregan.offerservice.resource.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@SuppressWarnings("unused") @RestControllerAdvice class InvalidArgumentAdvice {

    @ResponseBody @ExceptionHandler(IllegalArgumentException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
    String illegalArgument(IllegalArgumentException e) {
        return e.getMessage();
    }
}
