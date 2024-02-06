package br.com.api.productadministration.categories.services;


import br.com.api.productadministration.categories.enums.ExceptionMessagesEnum;
import br.com.api.productadministration.categories.exceptions.CategoryNotFoundException;
import br.com.api.productadministration.categories.mapper.CategoryMapper;
import br.com.api.productadministration.categories.model.dto.CategoryDTO;
import br.com.api.productadministration.categories.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    return repository.findByName(name).map(mapper::ToDTO)
            .orElseThrow(() -> new CategoryNotFoundException(name));
  }

  @Override
  public CategoryDTO getById(Long id) {
    return repository.findById(id).map(mapper::ToDTO)
            .orElseThrow(() -> new CategoryNotFoundException(id));
  }

  @Override
  public List<CategoryDTO> getAll() {
    return repository.findAll()
            .stream()
            .map(obj -> mapper.ToDTO(obj))
            .toList();
  }

  @Override
  public CategoryDTO update(CategoryDTO categoryDTO) {

    return mapper.ToDTO(repository.save(mapper.ToModel(categoryDTO)));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
