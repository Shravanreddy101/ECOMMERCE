package com.codeWithProjects.ecom.services.customer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeWithProjects.ecom.dto.FAQDTO;
import com.codeWithProjects.ecom.dto.ProductDTO;
import com.codeWithProjects.ecom.entity.FAQ;
import com.codeWithProjects.ecom.entity.Product;
import com.codeWithProjects.ecom.repository.FAQRepository;
import com.codeWithProjects.ecom.repository.ProductRepository;

@Service
public class CustomerProductServiceImpl implements CustomerProductService{

    @Autowired
    private final ProductRepository productRepository;
    private final FAQRepository faqRepository;

    public CustomerProductServiceImpl(ProductRepository productRepository, FAQRepository faqRepository){
        this.productRepository = productRepository;
        this.faqRepository = faqRepository;
    }



    @Override
    public List<ProductDTO> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDTO).collect(Collectors.toList());
    }


    @Override
    public List<ProductDTO> searchProductByTitle(String name){
        List<Product> products = productRepository.getAllByNameContaining(name);
        return products.stream().map(Product::getDTO).collect(Collectors.toList());
    }

    @Override
    public List<FAQDTO> getFAQ(Long productId){
        List<FAQ> faq = faqRepository.findAllByProductId(productId);
        return faq.stream().map(FAQ::getDTO).collect(Collectors.toList());
    }
}
