package br.com.api.productadministration.categories.mapper;

import br.com.api.productadministration.categories.model.Category;
import br.com.api.productadministration.categories.model.dto.CategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

  public Category ToModel(CategoryDTO categoryDTO) {
    return new Category().builder()
            .id(categoryDTO.id())
            .name(categoryDTO.name())
            .active(categoryDTO.active())
            .type(categoryDTO.type())
            .build();
  }
  public CategoryDTO ToDTO(Category category) {
    return new CategoryDTO.Builder()
            .id(category.getId())
            .name(category.getName())
            .active(category.getActive())
            .type(category.getType())
            .build();
  }
}
