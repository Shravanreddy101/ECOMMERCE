package com.codeWithProjects.ecom.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeWithProjects.ecom.dto.CategoryDTO;
import com.codeWithProjects.ecom.entity.Category;
import com.codeWithProjects.ecom.services.admin.category.CategoryService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminCategoryController {

    @Autowired
    private final CategoryService categoryService;


    public AdminCategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping("category")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryDTO){
        Category category = categoryService.createCategory(categoryDTO);
        System.out.println("Received Category Name: " + categoryDTO.getName());
        System.out.println("Received Category Description: " + categoryDTO.getDescription());
        return ResponseEntity.ok().body(category);
    }

    @GetMapping("get")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    





}
