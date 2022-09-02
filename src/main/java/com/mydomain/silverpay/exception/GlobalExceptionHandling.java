package com.mydomain.silverpay.exception;

import com.mydomain.silverpay.data.model.ReturnMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class GlobalExceptionHandling {


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBindingError(BindingResult bindingResult) {

        List<ObjectError> errorList = bindingResult.getAllErrors();
        List<ReturnMessage> messages = new ArrayList<>();

        for (ObjectError error : errorList) {
            messages.add(new ReturnMessage(false, error.getDefaultMessage(), "error"));
        }

        return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);


    }


    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleRuntimeException(Exception e) {

        System.out.println("++++++++++error: "+e.getMessage());
        e.printStackTrace();

        HttpHeaders headers = new HttpHeaders();
        headers.set("App-Error", e.getMessage());
        headers.set("Access-Control-Expose-Headers", "App-Error");

        return ResponseEntity.internalServerError().headers(headers)
                .body("error in server");
    }

    @ExceptionHandler(value = {HttpServerErrorException.InternalServerError.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleServerError(Exception e) {

        System.out.println(e.getMessage());

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        headers.put("app-Error", List.of(e.getMessage()));

        return new ResponseEntity<>
                ("internal server error response",
                        headers, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
