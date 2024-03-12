package br.com.api.productadministration.categories.mapper;

import br.com.api.productadministration.categories.model.Category;
import br.com.api.productadministration.categories.model.dto.CategoryDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MapperToDTO implements Function<Category, CategoryDTO> {
  @Override
  public CategoryDTO apply(Category category) {
    if (category == null) {
      return null;
    }
    return CategoryDTO.builder()
            .id(category.getId())
            .name(category.getName())
            .active(category.getActive())
            .type(category.getType())
            .build();
  }
}
