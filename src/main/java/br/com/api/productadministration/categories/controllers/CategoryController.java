package br.com.api.productadministration.categories.controllers;


import br.com.api.productadministration.categories.model.dto.CategoryDTO;
import br.com.api.productadministration.categories.services.CategoryService;
import br.com.api.productadministration.categories.services.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/categories")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController {

  @Autowired
  private CategoryService service;

  @PostMapping
  public ResponseEntity<CategoryDTO> create(@RequestBody @Valid CategoryDTO categoryDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createCategory(categoryDTO));
  }

  @GetMapping
  public ResponseEntity<CategoryDTO> getByName(@RequestParam("name") String name){
    return ResponseEntity.status(HttpStatus.OK).body(service.getByName(name));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> getById(@PathVariable Long id){
    return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
  }

  @GetMapping("/all")
  public ResponseEntity<List<CategoryDTO>> getAll(){
    return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
  }

  @PutMapping("/{id}")
  public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
    return ResponseEntity.status(HttpStatus.OK).body(service.update(id, categoryDTO));
  }
  @PatchMapping("/{id}")
  public ResponseEntity<CategoryDTO> updatePartial(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
    return ResponseEntity.status(HttpStatus.OK).body(service.updatePartial(id, fields));
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Long id){
    service.delete(id);
    ResponseEntity.status(HttpStatus.NO_CONTENT);
  }


}
