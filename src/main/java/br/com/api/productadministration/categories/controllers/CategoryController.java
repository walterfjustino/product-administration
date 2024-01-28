package br.com.api.productadministration.categories.controllers;


import br.com.api.productadministration.categories.model.dto.CategoryDTO;
import br.com.api.productadministration.categories.services.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController {

  @Autowired
  private CategoryServiceImpl service;

  @PostMapping
  public ResponseEntity<CategoryDTO> create(@RequestBody @Valid CategoryDTO categoryDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createCategory(categoryDTO));
  }

  @GetMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<List<CategoryDTO>> getAll(){
    return ResponseEntity.ok(service.getAll());
  }

}