package com.example.demo.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;


//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Getter
//@Setter
public class CategoryDTO {
    @NotEmpty(message = "Name is required")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
