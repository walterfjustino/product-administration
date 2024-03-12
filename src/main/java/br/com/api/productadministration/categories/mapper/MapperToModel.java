package br.com.api.productadministration.categories.mapper;

import br.com.api.productadministration.categories.model.Category;
import br.com.api.productadministration.categories.model.dto.CategoryDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MapperToModel implements Function<CategoryDTO, Category> {
  @Override
  public Category apply(CategoryDTO categoryDTO) {
    if (categoryDTO == null) {
      return null;
    }
    return Category.builder()
            .id(categoryDTO.id())
            .name(categoryDTO.name())
            .active(categoryDTO.active())
            .type(categoryDTO.type())
            .build();
  }
}
