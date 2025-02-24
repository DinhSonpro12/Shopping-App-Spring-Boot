package com.example.demo;

import com.example.demo.dtos.CategoryDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopappApplication.class, args);

//		CategoryDTO categoryDTO = new CategoryDTO();
//		categoryDTO.setName("Electronics");
//		System.out.println(categoryDTO.getName());
	}

}
