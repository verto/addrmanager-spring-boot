package me.verto.addrmanager;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.verto.addrmanager.zipcode.ZipCodeInvalidException;

import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

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

    response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    try {
      String json = new ObjectMapper().writeValueAsString(new Error(response.getStatus(), e.getMessage()));
      response.getWriter().write(json);
    } catch (Exception ex) {
    }

  }

}
