package br.com.api.productadministration.categories;

import br.com.api.productadministration.categories.mapper.MapperToDTO;
import br.com.api.productadministration.categories.mapper.MapperToModel;
import br.com.api.productadministration.categories.model.Category;
import br.com.api.productadministration.categories.model.dto.CategoryDTO;
import br.com.api.productadministration.categories.repositories.CategoryRepository;
import br.com.api.productadministration.categories.services.CategoryServiceImpl;
import br.com.api.productadministration.utils.CsvToCategory;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CategoryTest {
  private static final String scenariosPath = "scenarios/categoryEndpoint.yml";

  @InjectMocks
  private CategoryServiceImpl service;
  @Mock
  private CategoryRepository repository;
  @Spy
  private MapperToModel mapperToModel;
  @Spy
  private MapperToDTO mapperToDTO;
  @Captor
  private ArgumentCaptor<Category> categoryArgCaptor;
  @Test
  void shouldCreateCategorySuccessfully() throws Exception {

   final var expectedCategoryCreateDTO = CategoryDTO.builder()
            .id(4L)
            .name("Brinquedos")
            .active(true)
            .type("NORMAL")
            .build();

    var entityToSave = mapperToModel.apply(expectedCategoryCreateDTO);

    lenient().when(repository.save(any(Category.class))).thenReturn(entityToSave);

    final CategoryDTO createdCategoryDTO = service.createCategory(expectedCategoryCreateDTO);

    verify(repository).save(categoryArgCaptor.capture());

//    MatcherAssert.assertThat(createdCategoryDTO, Matchers.is(Matchers.equalTo(expectedCategoryCreateDTO)));
    assertAll(
            () -> MatcherAssert.assertThat(createdCategoryDTO, Matchers.is(Matchers.equalTo(expectedCategoryCreateDTO))),
            () -> assertEquals(createdCategoryDTO, expectedCategoryCreateDTO),
            () -> assertEquals(expectedCategoryCreateDTO.name(), createdCategoryDTO.name()),
            () -> assertEquals(expectedCategoryCreateDTO.id(), createdCategoryDTO.id())
    );
  }


  //@ParameterizedTest(name = "[{index}] {arguments}")//(name = "[{index}] => expected result with values: {arguments}") //id = {0} , name = {1} , active = {2} , type = {3}
  @ParameterizedTest(name = "#[{index}] Should assert equals for object provided as argument to repository for the" +
                            " values: id = {0} , name = {1} , active = {2} , type = {3}")
  @CsvFileSource(resources = "/csv/CreateCategory.csv", numLinesToSkip = 1, delimiter = ',')
  void shouldReturnCreatedCategories(Long id, String name, Boolean active, String type) throws Exception {

     var expectedCreatedCategoryDTO = CategoryDTO.builder()
            .id(id)
            .name(name)
            .active(active)
            .type(type)
            .build();

    final var categoryToCreate = mapperToModel.apply(expectedCreatedCategoryDTO);
    final var categoryCreated = mapperToModel.apply(expectedCreatedCategoryDTO);

    lenient().when(repository.save(categoryCreated)).thenReturn(categoryToCreate);

    final var result = service.createCategory(expectedCreatedCategoryDTO);

    verify(repository).save(categoryArgCaptor.capture());

    assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(name, categoryArgCaptor.getValue().getName()),
            () -> assertEquals(name, result.name()),
            () -> assertEquals(id, categoryArgCaptor.getValue().getId()),
            () -> assertEquals(id, result.id())
    );

  }

  @ParameterizedTest(name = "#[{index}] => expected result with arguments: => id = {0} , name = {1} , active = {2} , type = {3}")
  @CsvFileSource(resources = "/csv/CreateCategory.csv", numLinesToSkip = 1, delimiter = ',')
  void shouldReturnCategoryById(Long id, String name, Boolean active, String type){
    final var expectedCategoryCreateDTO = CategoryDTO.builder()
            .id(id)
            .name(name)
            .active(active)
            .type(type)
            .build();

    final var category = mapperToModel.apply(expectedCategoryCreateDTO);

    when(repository.getReferenceById(expectedCategoryCreateDTO.id())).thenReturn(category);

    final var result = service.getById(id);

    verify(repository).getReferenceById(categoryArgCaptor.getValue().getId());

    assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(name, categoryArgCaptor.getValue().getName()),
            () -> assertEquals(name, result.name()),
            () -> assertEquals(id, categoryArgCaptor.getValue().getId()),
            () -> assertEquals(id, result.id())
    );

}
  @ParameterizedTest(name = "#[{index}] => expected result with arguments: => id = {0} , name = {1} , active = {2} , type = {3}")
  @CsvFileSource(resources = "/csv/GetAllCategories.csv", numLinesToSkip = 1, delimiter = ',')
    void shouldReturnAllCategories(@CsvToCategory Category category) throws Exception {

    final var expectedReturnedListCategory = new ArrayList<Category>();
    expectedReturnedListCategory.add(category);

    final var expectedReturnedListCategoryDTO = new ArrayList<CategoryDTO>();
    expectedReturnedListCategoryDTO.addAll(Collections.singletonList(mapperToDTO.apply(category)));

    when(repository.findAll()).thenReturn(expectedReturnedListCategory);

    final List<CategoryDTO> result = service.getAll();

    verify(repository).findAll();

    assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(expectedReturnedListCategoryDTO, result )
    );
  }
}
