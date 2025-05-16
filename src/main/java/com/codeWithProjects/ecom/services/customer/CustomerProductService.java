package com.codeWithProjects.ecom.services.customer;

import java.util.List;

import com.codeWithProjects.ecom.dto.FAQDTO;
import com.codeWithProjects.ecom.dto.ProductDTO;

public interface CustomerProductService {

    public List<ProductDTO> searchProductByTitle(String title);

    public List<ProductDTO> getAllProducts();

    public List<FAQDTO> getFAQ(Long productId);
}
