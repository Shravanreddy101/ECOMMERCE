package com.codeWithProjects.ecom.services.admin.adminproduct;

import java.io.IOException;
import java.util.List;

import com.codeWithProjects.ecom.dto.ProductDTO;
import com.codeWithProjects.ecom.entity.Product;
import com.codeWithProjects.ecom.repository.ProductRepository;

public interface AdminProductService {


    public ProductDTO addProduct(ProductDTO productDTO) throws IOException;

    public List<ProductDTO> getAllProducts();

    public ProductDTO findById(Long productId);

    public Product findByName(ProductDTO productDTO);

    public List<ProductDTO> getAllByNameContaining(String name);

    public boolean deleteProduct(Long id);

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) throws IOException;
}
