package br.com.api.productadministration.categories;

import br.com.api.productadministration.categories.mapper.CategoryMapper;
import br.com.api.productadministration.categories.model.Category;
import br.com.api.productadministration.categories.repositories.CategoryRepository;
import br.com.api.productadministration.categories.services.CategoryServiceImpl;
import br.com.api.productadministration.configuration.LoggerExtension;
import br.com.api.productadministration.configuration.YamlLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@ExtendWith(LoggerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
public class CategoryIntegrationTest {
  private static final String scenariosPath = "scenarios/categoryEndpoint.yml";

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres =
          new PostgreSQLContainer<>("postgres:16.0");

 @Autowired
 private CategoryRepository repository;

  @Autowired
  private CategoryServiceImpl service;

  @Autowired
  private CategoryMapper mapper;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    repository.deleteAll();
    createData();
  }

  @Test
  void shouldCreateCategoruSuccessfully() throws Exception {
    final YamlLoader payload = new YamlLoader(scenariosPath, "shouldBeCreateCategory");

    mockMvc.perform(MockMvcRequestBuilders.post("/api/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(payload.getInput()))
            .andDo(print())
            .andExpect(status().isCreated());

    await().pollInterval(Duration.ofSeconds(5)).atMost(30, SECONDS).untilAsserted(() -> {
      assertThat(repository.findByName("Brinquedos")).isNotEmpty();
    });
  }

  @Test
  void shouldReturnCategories() throws Exception {

    final YamlLoader responseBody = new YamlLoader(scenariosPath, "shouldBeReturnCategories");

    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/all")
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(responseBody.getInput()))
            .andExpect(jsonPath("$[0].name").value("Monitores"))
            .andExpect(jsonPath("$[1].name").value("Tvs"))
            .andExpect(jsonPath("$[2].name").value("Celulares"));

    await().pollInterval(Duration.ofSeconds(5)).atMost(30, SECONDS).untilAsserted(() -> {
      assertThat(repository.findByName("Tvs")).isNotEmpty();
    });
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
