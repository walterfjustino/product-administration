package br.com.api.productadministration.categories.services;


import br.com.api.productadministration.categories.model.Category;
import br.com.api.productadministration.categories.model.dto.CategoryDTO;
import br.com.api.productadministration.categories.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryServiceImpl implements CategoryService {


  @Autowired
  private CategoryRepository repository;

  @Override
  public CategoryDTO createCategory(CategoryDTO categoryDTO) {
    Category category = mapperToModel(categoryDTO);
    Category createdCategory = repository.save(category);
    return mapperToDTO(createdCategory);
  }

  private Category mapperToModel(CategoryDTO categoryDTO) {
    return new Category().builder()
            .id(categoryDTO.id())
            .name(categoryDTO.name())
            .active(categoryDTO.active())
            .type(categoryDTO.type())
            .build();
  }
  private CategoryDTO mapperToDTO(Category category) {
    return new CategoryDTO.Builder()
            .id(category.getId())
            .name(category.getName())
            .active(category.getActive())
            .type(category.getType())
            .build();
  }

  @Override
  public CategoryDTO getByName(String name) {
    return null;
  }

  @Override
  public List<CategoryDTO> getAll() {
    return repository.findAll()
            .stream()
            .map(this::mapperToDTO)
            .toList();
  }

  @Override
  public CategoryDTO update(CategoryDTO categoryDTO) {
    return null;
  }

  @Override
  public void delete(Long id) {

  }
}
