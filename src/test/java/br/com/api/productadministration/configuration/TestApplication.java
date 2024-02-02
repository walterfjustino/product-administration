package br.com.api.productadministration.configuration;

import org.springframework.boot.SpringApplication;

public class TestApplication {

  public static void main(String[] args){
    SpringApplication.from(TestApplication::main)
            .with(TestContainersConfig.class)
            .run(args);
  }
}
