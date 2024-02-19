package br.com.api.productadministration.categories.services;


import br.com.api.productadministration.categories.exceptions.CategoryNotFoundException;
import br.com.api.productadministration.categories.exceptions.CategoryNotFoundExceptionIsNotTheSame;
import br.com.api.productadministration.categories.mapper.CategoryMapper;
import br.com.api.productadministration.categories.model.Category;
import br.com.api.productadministration.categories.model.dto.CategoryDTO;
import br.com.api.productadministration.categories.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryMapper mapper;

  @Autowired
  private CategoryRepository repository;

  @Override
  @Transactional
  public CategoryDTO createCategory(CategoryDTO categoryDTO) {
    return mapper.ToDTO(repository.save(mapper.ToModel(categoryDTO)));
  }

  @Override
  public CategoryDTO getByName(String name) {
    return repository.findByName(name)
            .map(mapper::ToDTO)
            .orElseThrow(() -> new CategoryNotFoundException(name));
  }

//  @Override
//  public CategoryDTO getById(Long id) {
//    return repository.findById(id)
//            .map(mapper::ToDTO)
//            .orElseThrow(() -> new CategoryNotFoundException(id));
//  }

  @Override
  public CategoryDTO getById(Long id) {
    return mapper.ToDTO(repository.getReferenceById(id));
  }

  @Override
  public List<CategoryDTO> getAll() {
    return repository.findAll()
            .stream()
            .map(obj -> mapper.ToDTO(obj))
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
     return mapper.ToDTO(repository.save(foundCategory));
  }

  @Override
  public CategoryDTO updatePartial(Long id, Map<String, Object> fields) {
    var foundCategory = mapper.ToModel(getById(id));
    fields.forEach((key, value) -> {
      Field fieldKey = ReflectionUtils.findRequiredField(Category.class, key);
      fieldKey.setAccessible(true);
      ReflectionUtils.setField(fieldKey, foundCategory, value);
    });
    return mapper.ToDTO(repository.save(foundCategory));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
