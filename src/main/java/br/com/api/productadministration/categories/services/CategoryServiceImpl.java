package br.com.api.productadministration.categories.services;


import br.com.api.productadministration.categories.exceptions.CategoryNotFoundException;
import br.com.api.productadministration.categories.mapper.MapperToDTO;
import br.com.api.productadministration.categories.mapper.MapperToModel;
import br.com.api.productadministration.categories.model.Category;
import br.com.api.productadministration.categories.model.dto.CategoryDTO;
import br.com.api.productadministration.categories.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository repository;
  @Autowired
  private MapperToDTO mapperToDTO;
  @Autowired
  private MapperToModel mapperToModel;

  @Override
  @Transactional
  public CategoryDTO createCategory(CategoryDTO categoryDTO) {
    Category entityToSave = mapperToModel.apply(categoryDTO);
    Category entitySaved = repository.save(entityToSave);
    CategoryDTO dto = mapperToDTO.apply(entitySaved);
    return dto;
  }

  @Override
  public CategoryDTO getByName(String name) {
    return repository.findByName(name)
            .map(mapperToDTO)
            .orElseThrow(() -> new CategoryNotFoundException(name));
  }

//  @Override
//  public CategoryDTO getById(Long id) {
//    return repository.findById(id)
//            .map(mapperToDTO)
//            .orElseThrow(() -> new CategoryNotFoundException(id));
//  }

  @Override
  public CategoryDTO getById(Long id) {
    return mapperToDTO.apply(repository.getReferenceById(id));
  }

  @Override
  public List<CategoryDTO> getAll() {
    return repository.findAll()
            .stream()
            .map(mapperToDTO)
            .toList();
  }

  @Override
  public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
    var foundCategory = repository.findById(id)
            .map(obj -> {
              obj.setName(categoryDTO.name());
              obj.setActive(categoryDTO.active());
              obj.setType(categoryDTO.type());
            return obj;
            })
            .orElseThrow(() -> new CategoryNotFoundException(id));
     return mapperToDTO.apply(repository.save(foundCategory));
  }

  @Override
  public CategoryDTO updatePartial(Long id, Map<String, Object> fields) {
    var foundCategory = mapperToModel.apply(getById(id));
    fields.forEach((key, value) -> {
      Field fieldKey = ReflectionUtils.findRequiredField(Category.class, key);
      fieldKey.setAccessible(true);
      ReflectionUtils.setField(fieldKey, foundCategory, value);
    });
    return mapperToDTO.apply(repository.save(foundCategory));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
