package com.codeWithProjects.ecom.services.admin.category;

import java.util.List;

import com.codeWithProjects.ecom.dto.CategoryDTO;
import com.codeWithProjects.ecom.entity.Category;

public interface CategoryService {

    public Category createCategory(CategoryDTO categoryDTO);

    public List<Category> getAllCategories();
}
