package br.com.api.productadministration.categories.exceptions;

import br.com.api.productadministration.categories.enums.ExceptionMessagesEnum;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends EntityNotFoundException {
//  public CategoryNotFoundException(Long id) {
//    super(String.format("""
//            Category with id: %s not found!\s
//            """, id));
//  }
//
//  public CategoryNotFoundException(String name) {
//    super(String.format("""
//            Category with with name %s not found!\s
//            """, name));
//  }
public CategoryNotFoundException( String message, Long id) {
  super(String.format(ExceptionMessagesEnum.CATEGORY_ID_NOT_FOUND.getMessage(), id));
}

  public CategoryNotFoundException(String message, String name) {
    super(String.format(ExceptionMessagesEnum.CATEGORY_ID_NOT_FOUND.getMessage(), name) );
  }
}
