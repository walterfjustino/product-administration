package br.com.api.productadministration.categories.enums;

import org.springframework.http.HttpStatus;

public enum ExceptionMessagesEnum {

  CATEGORY_ID_NOT_FOUND(404001, "Category not found for the category id: %s informed", HttpStatus.NOT_FOUND),
  CATEGORY_BY_NAME_NOT_FOUND(404002, "Category not found for the category name: %s informed", HttpStatus.NOT_FOUND);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;

  ExceptionMessagesEnum(Integer code, String message, HttpStatus httpStatus) {
    this.code = code;
    this.message = message;
    this.httpStatus = httpStatus;
  }

  public Integer getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

}

