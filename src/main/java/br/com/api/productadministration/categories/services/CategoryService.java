package br.com.api.productadministration.categories.services;



import br.com.api.productadministration.categories.model.dto.CategoryDTO;

import java.util.List;
import java.util.Map;

public interface CategoryService {

  public CategoryDTO createCategory(CategoryDTO categoryDTO);
  public CategoryDTO getByName(String name);
  public CategoryDTO getById(Long id);
  public List<CategoryDTO> getAll();
  public CategoryDTO update(Long id, CategoryDTO categoryDTO);
  public CategoryDTO updatePartial(Long id, Map<String, Object> fields);
  void delete(Long id);
}
