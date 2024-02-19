package br.com.api.productadministration.categories.exceptions;

import br.com.api.productadministration.categories.enums.ExceptionMessagesEnum;
import jakarta.persistence.EntityNotFoundException;

public class CategoryNotFoundExceptionIsNotTheSame extends EntityNotFoundException {

  public CategoryNotFoundExceptionIsNotTheSame(Long id) {
    super(String.format(ExceptionMessagesEnum.CATEGORY_ID_INFORMED_IS_NOT_THE_SAME_REGISTERED.getMessage(), id));
  }
}
