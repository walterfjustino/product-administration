package br.com.api.productadministration.categories.services;



import br.com.api.productadministration.categories.model.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

  public CategoryDTO createCategory(CategoryDTO categoryDTO);
  public CategoryDTO getByName(String name);
  public List<CategoryDTO> getAll();
  public CategoryDTO update(CategoryDTO categoryDTO);
  void delete(Long id);
}
