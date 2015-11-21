package me.verto.addrmanager;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.verto.addrmanager.zipcode.ZipCodeInvalidException;
import me.verto.addrmanager.zipcode.ZipCodeNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static class Errors implements Serializable {

        private static final long serialVersionUID = -3028299110880753054L;

        private Integer statusCode;
        private List<Error> errors = new ArrayList<>();

        public Errors(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public Errors(Integer statusCode, List<Error> errors) {
            this.statusCode = statusCode;
            this.errors = errors;
        }

        public void add(Error error) { this.errors.add(error); }
        public List<Error> getErrors() { return this.errors; }
        public Integer getStatusCode() { return statusCode; }

        public static Errors valueOf(Integer statusCode, String message) {
            Errors errors = new Errors(statusCode);
            errors.add(new Error(message));
            return errors;
        }
    }

    public static class Error implements Serializable {
        private static final long serialVersionUID = 142008666154563228L;

        private final String message;

        public Error(String message) {  this.message = message; }
        public String getMessage() { return message; }
    }

    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ZipCodeInvalidException.class)
    public void zipCodeInvalid(ZipCodeInvalidException e, HttpServletResponse response) {

        log.warn("ZipCodeInvalidException: " + e.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        Errors errors = Errors.valueOf(response.getStatus(), e.getMessage());
        writeErrorResponse(errors, response);

    }

    @ResponseStatus(value=HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ZipCodeNotFoundException.class)
    public void zipCodeNotFound(ZipCodeNotFoundException e, HttpServletResponse response) {

        log.warn("ZipCodeNotFoundException: " + e.getMessage());
        response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        Errors errors = Errors.valueOf(response.getStatus(), e.getMessage());
        writeErrorResponse(errors, response);

    }

    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void entityNotFound(EntityNotFoundException e) {
        log.warn("EntityNotFoundException: " + e.getMessage());
    }

    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void messageNotReadble(HttpMessageNotReadableException e, HttpServletResponse response) {
        log.warn("HttpMessageNotReadableException: " + e.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        Exception ex = e.getRootCause() != null ? (Exception)e.getRootCause() : e;
        Errors errors = Errors.valueOf(response.getStatus(), ex.getMessage());
        writeErrorResponse(errors, response);
    }

    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void messageNotValid(MethodArgumentNotValidException e, HttpServletResponse response) {
        log.warn("MethodArgumentNotValidException: " + e.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        List<FieldError> objectErrors = e.getBindingResult().getFieldErrors();

        List<Error> errorsList = objectErrors.stream()
            .map(o -> new Error(o.getField() + " " + o.getDefaultMessage()))
            .collect(Collectors.toList());

        writeErrorResponse(new Errors(response.getStatus(), errorsList), response);
    }


    private void writeErrorResponse(Errors errors, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String json = new ObjectMapper().writeValueAsString(errors);
            response.getWriter().write(json);
        } catch (Exception ex) {
            log.error("cannot possible to create json content.", ex);
        }
    }

}
