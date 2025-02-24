package com.example.demo.controllers;

import com.example.demo.dtos.ProductDTO;
import com.example.demo.dtos.ProductImageDTO;
import com.example.demo.models.Product;
import com.example.demo.models.ProductImage;
import com.example.demo.projections.AllProductResponse;
import com.example.demo.projections.ProductProjection;
import com.example.demo.services.ProductService;
import com.github.javafaker.Faker;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")

public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<?> getProducts(@RequestParam("Page") int page, @RequestParam("limit") int limit

    ) {
        try {
            if (page < 1 || limit < 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Page and limit must be greater than 1");
            }

            Page<ProductProjection> productPage = productService.getAllProduct(page - 1, limit);
//            List<Product> products = productPage.getContent();
            AllProductResponse products = new AllProductResponse(productPage.getContent(), productPage.getTotalPages());
            return ResponseEntity.ok(products);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductByID(@PathVariable("id") Long ProductID) {
        try {
            return ResponseEntity.ok(productService.getProductById(ProductID));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postProduct(
//            @Valid @RequestBody ProductDTO productDTO,
            @Valid @ModelAttribute ProductDTO productDTO,
//            @RequestPart("file") MultipartFile thumbnail

            BindingResult bindingResult, HttpServletRequest request
//            @RequestPart("file") MultipartFile thumbnail
//            @RequestParam("thumnail") MultipartFile thumbnail
//            @RequestBody Map<String, Object> body

    ) {

        try {
//            nếu có lỗi của các fiel trong body
            if (bindingResult.hasErrors()) {
                List<String> errorsMessages = bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();

                return ResponseEntity.badRequest().body(errorsMessages);

            }
            // Kiểm tra số lượng file upload có cùng tên "thumbnail"
            if (request.getParts().stream().filter(part -> "thumbnail".equals(part.getName()) && part.getSize() > 0).count() > 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Only support upload 1 file thumbnail ");
            }


            Product newProduct = productService.createProduct(productDTO);

            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putProduct(@PathVariable("id") Long id,
//            @Valid @RequestBody ProductDTO productDTO,
                                        @Valid @ModelAttribute ProductDTO productDTO,

                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorsMessages = bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();

            return ResponseEntity.badRequest().body(errorsMessages);
        }

        try {
            productService.updateProductById(productDTO, id);
            return ResponseEntity.ok("update product have id" + id + "successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long productID) {
        try {
            productService.deleteProductById(productID);
            return ResponseEntity.ok("delete product have id" + productID);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping(value = "/upload/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@PathVariable("productId") Long productId, @RequestParam(value = "files") List<MultipartFile> listFile) {
        try {
//            if (listFile != null) { - không cần kiểm tra null vì neu ko co file gguiwrw lên từ requesrt thì nó se bị lỗi un suport daât type
            if (listFile.size() > 5) {
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Max file upload is 5");
            }

            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile fileImage : listFile) {
                if (!fileImage.isEmpty()) {
                    if (fileImage.getSize() > 10 * 1024 * 1024) {
                        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File size is too large! Max size is 10MB");
                    }

                    String contentType = fileImage.getContentType();
                    if (contentType == null || !contentType.startsWith("image/")) {
                        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Unsupported media type! Only support image file");
                    }

                    ProductImage productImage = productService.uploadImage(new ProductImageDTO(productId, fileImage));
                    productImages.add(productImage);
                }
            }


            return ResponseEntity.ok("Upload image successfully" + productImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/gennarateFakeProducts")
    public ResponseEntity<?> generateFakeProducts() {

        Faker faker = new Faker();
        for (int i = 0; i < 1000000; i++) {
            String productName = faker.commerce().productName();
            if(productService.existsByName(productName)){
                continue;
            }
            ProductDTO productDTO = new ProductDTO();
            productDTO.setName(productName);
            productDTO.setPrice((float) faker.number().randomDouble(2, 1, 1000));
            productDTO.setDescription(faker.lorem().sentence());
            productDTO.setCategoryId((long) faker.number().numberBetween(1, 5));
            try {
                productService.createProduct(productDTO);

            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        return ResponseEntity.ok("Generate fake products successfully");
    }

    private String StoreFile(MultipartFile thumbnail) throws IOException {

        String newFileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(thumbnail.getOriginalFilename());
        Path uploadDir = Paths.get("uploads");
        Files.createDirectories(uploadDir);

        Path destination = Paths.get("uploads/" + newFileName);
        Files.copy(thumbnail.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return newFileName;
    }
}
