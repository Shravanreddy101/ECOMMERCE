package com.codeWithProjects.ecom.services.admin.adminproduct;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeWithProjects.ecom.dto.ProductDTO;
import com.codeWithProjects.ecom.entity.Category;
import com.codeWithProjects.ecom.entity.Product;
import com.codeWithProjects.ecom.repository.CategoryRepository;
import com.codeWithProjects.ecom.repository.ProductRepository;

@Service
public class AdminProductServiceImpl implements AdminProductService{

    @Autowired
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public AdminProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }



    @Override
    public ProductDTO addProduct(ProductDTO productDTO) throws IOException{
        
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setImg(productDTO.getImg().getBytes());
        
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow();

        product.setCategory(category);
        
        return productRepository.save(product).getDTO();
    }

    @Override
    public List<ProductDTO> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDTO).collect(Collectors.toList());
    }


    @Override
    public List<ProductDTO> getAllByNameContaining(String name){
        List<Product> products = productRepository.getAllByNameContaining(name);
        return products.stream().map(Product::getDTO).collect(Collectors.toList());
    }


    @Override
    public ProductDTO findById(Long productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(!optionalProduct.isPresent()){
            return null;
        }
        Product product = optionalProduct.get();
        return product.getDTO();
    }


    @Override
    public Product findByName(ProductDTO productDTO){
        Optional<Product> optionalProduct = productRepository.findByName(productDTO.getName());
        if(!optionalProduct.isPresent()){
            return null;
        }
        Product product = optionalProduct.get();
        return product;
    }



    @Override
    public boolean deleteProduct(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) throws IOException{
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = optionalProduct.get();
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow();
        

        if(productDTO.getName() != null){
            product.setName(productDTO.getName());
        }
        if(productDTO.getPrice() != null){
            product.setPrice(productDTO.getPrice());
        }
        if(productDTO.getDescription() != null){
            product.setDescription(productDTO.getDescription());
        }
        if (category != null) {
            product.setCategory(category);
        }
        if (productDTO.getImg() != null && !productDTO.getImg().isEmpty()) {
                byte[] imgBytes = productDTO.getImg().getBytes();
                product.setImg(imgBytes);
        }
        return productRepository.save(product).getDTO();
        
    }




}
