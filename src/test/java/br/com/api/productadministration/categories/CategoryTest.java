package br.com.api.productadministration.categories;

import br.com.api.productadministration.categories.mapper.CategoryMapper;
import br.com.api.productadministration.categories.model.Category;
import br.com.api.productadministration.categories.model.dto.CategoryDTO;
import br.com.api.productadministration.categories.repositories.CategoryRepository;
import br.com.api.productadministration.categories.services.CategoryServiceImpl;
import br.com.api.productadministration.configuration.LoggerExtension;
import br.com.api.productadministration.configuration.YamlLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({LoggerExtension.class, MockitoExtension.class})
@AutoConfigureMockMvc
public class CategoryTest {
  private static final String scenariosPath = "scenarios/categoryEndpoint.yml";

 @Mock
 private CategoryRepository repository;

  @InjectMocks
  private CategoryServiceImpl service;


  private final CategoryMapper mapper;

  @Autowired
  private MockMvc mockMvc;

  public CategoryTest(CategoryMapper mapper) {
    this.mapper = mapper;
  }

  @BeforeEach
  void setUp() {
    repository.deleteAll();
//    createData();
  }

  @Test
  void shouldCreateCategoruSuccessfully() throws Exception {
    final YamlLoader payload = new YamlLoader(scenariosPath, "shouldBeCreateCategory");

    var expectedCategoryCreateDTO = new CategoryDTO(4L, "Brinquedos", true, "NORMAL");

    var expectedCreatedCategory = repository.save(mapper.ToModel(expectedCategoryCreateDTO));

    Mockito.when(service.createCategory(expectedCategoryCreateDTO))
            .thenReturn(mapper.ToDTO(expectedCreatedCategory));

    mockMvc.perform(MockMvcRequestBuilders.post("/api/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(payload.getInput()))
            .andDo(print())
            .andExpect(status().isCreated());

       assertThat(repository.findByName("Brinquedos")).isNotEmpty();
  }

  @Test
  void shouldReturnCategories() throws Exception {

    final YamlLoader responseBody = new YamlLoader(scenariosPath, "shouldBeReturnCategories");

    mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/all")
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(responseBody.getInput()))
            .andExpect(jsonPath("$[0].name").value("Monitores"))
            .andExpect(jsonPath("$[1].name").value("Tvs"))
            .andExpect(jsonPath("$[2].name").value("Celulares"));

      assertThat(repository.findByName("Tvs")).isNotEmpty();
  }

  private void createData() {
    service.createCategory(mapper.ToDTO(
            new Category().builder()
                    .id(1L)
                      .name("Monitores")
                        .type("NORMAL")
                        .active(true)
                          .build()));
    service.createCategory(mapper.ToDTO(
            new Category().builder()
                    .id(2L)
                      .name("Tvs")
                        .type("NORMAL")
                          .active(true)
                            .build()));
    service.createCategory(mapper.ToDTO(
            new Category().builder()
                    .id(3L)
                      .name("Celulares")
                        .type("NORMAL")
                          .active(true)
                            .build()));
  }
}
