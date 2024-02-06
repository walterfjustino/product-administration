package br.com.api.productadministration.categories.handler.error;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(Integer code,
                            String status,
                            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
                            LocalDateTime timestamp,
                            String message,
                            List<String> errors) {

  public static class Builder{

    private Integer code;
    private String status;
    LocalDateTime timestamp;
    String message;
    List<String> errors;

    public Builder withCode(Integer code){
      this.code = code;
      return this;
    }

    public Builder withStatus(String status){
      this.status = status;
      return this;
    }

    public Builder withTimestamp(LocalDateTime timestamp){
      this.timestamp = timestamp;
      return this;
    }

    public Builder withMessage(String message){
      this.message = message;
      return this;
    }

    public Builder withErrors(List<String> errors){
      this.errors = errors;
      return this;
    }
    public ErrorResponse build() {
      return new ErrorResponse(code, status, timestamp, message, errors);
    }
  }
}
