package me.verto.addrmanager;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.verto.addrmanager.zipcode.ZipCodeInvalidException;
import me.verto.addrmanager.zipcode.ZipCodeNotFoundException;

import java.io.Serializable;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public final class Error implements Serializable {

    private static final long serialVersionUID = 142008666154563228L;

    private final Integer statusCode;
    private final String errorMessage;

    public Error(Integer statusCode, String message) {
      this.statusCode = statusCode;
      this.errorMessage = message;
    }

    public Integer getStatusCode() {
      return statusCode;
    }

    public String getErrorMessage() {
      return errorMessage;
    }
  }

  @ResponseStatus(value=HttpStatus.UNPROCESSABLE_ENTITY)
  @ExceptionHandler(ZipCodeInvalidException.class)
  public void zipCodeInvalid(ZipCodeInvalidException e, HttpServletResponse response) {

    log.warn("ZipCodeInvalidException: " + e.getMessage());
    response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
    writeErrorResponse(e, response);

  }

  @ResponseStatus(value=HttpStatus.UNPROCESSABLE_ENTITY)
  @ExceptionHandler(ZipCodeNotFoundException.class)
  public void zipCodeNotFound(ZipCodeNotFoundException e, HttpServletResponse response) {

    log.warn("ZipCodeNotFoundException: " + e.getMessage());
    response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
    writeErrorResponse(e, response);

  }

  @ResponseStatus(value=HttpStatus.NOT_FOUND)
  @ExceptionHandler(EntityNotFoundException.class)
  public void entityNotFound(EntityNotFoundException e) {
    log.warn("EntityNotFoundException: " + e.getMessage());
  }

  private void writeErrorResponse(Exception e, HttpServletResponse response) {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    try {
      String json = new ObjectMapper().writeValueAsString(new Error(response.getStatus(), e.getMessage()));
      response.getWriter().write(json);
    } catch (Exception ex) {
      log.error("cannot possible to create json content.", ex);
    }
  }

}
