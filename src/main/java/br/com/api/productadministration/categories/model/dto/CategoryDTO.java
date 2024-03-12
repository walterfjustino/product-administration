package br.com.api.productadministration.categories.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;


@Builder
public record CategoryDTO(
        Long id,
        @NotNull
        @Size(min = 3, max = 200, message = "Maximum of characters permited exceed of the the limit")
        String name,
        @NotNull
        Boolean active,
        @NotNull
        String type) {

//  public static class Builder {
//
//    private Long id;
//    private String name;
//    private Boolean active;
//    private String type;
//
//    public Builder id(Long id) {
//      this.id = id;
//      return this;
//    }
//
//    public Builder name(String name) {
//      this.name = name;
//      return this;
//    }
//
//    public Builder active(Boolean active) {
//      this.active = active;
//      return this;
//    }
//
//    public Builder type(String type) {
//      this.type = type;
//      return this;
//    }
//
//    public CategoryDTO build () {
//      return new CategoryDTO(id, name, active, type);
//    }
//  }
}
