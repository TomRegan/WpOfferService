package io.github.tomregan.offerservice.resource.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@SuppressWarnings("unused") @RestControllerAdvice class NoOfferAdvice {

    @ResponseBody @ExceptionHandler(NoSuchElementException.class) @ResponseStatus(HttpStatus.NOT_FOUND)
    String noSuchElement(NoSuchElementException e) {
        return e.getMessage();
    }
}
