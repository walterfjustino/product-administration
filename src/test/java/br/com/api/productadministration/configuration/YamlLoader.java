package br.com.api.productadministration.configuration;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

public class YamlLoader {

  private final YamlPropertiesFactoryBean yaml;
  private final String scenario;

  public YamlLoader(String fileLocation, String scenario) {
    yaml = new YamlPropertiesFactoryBean();
    yaml.setResources(new ClassPathResource(fileLocation));
    this.scenario = scenario;
  }

  public String getInput() {
    return yaml.getObject().getProperty(scenario + ".input");
  }
}
