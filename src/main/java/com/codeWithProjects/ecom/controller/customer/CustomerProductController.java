package com.codeWithProjects.ecom.controller.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeWithProjects.ecom.dto.FAQDTO;
import com.codeWithProjects.ecom.dto.ProductDTO;
import com.codeWithProjects.ecom.services.customer.CustomerProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerProductController {

    @Autowired
    private final CustomerProductService customerProductService;

    public CustomerProductController(CustomerProductService customerProductService){
        this.customerProductService = customerProductService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> findAll(){
        List<ProductDTO> productDTO = customerProductService.getAllProducts();
        return ResponseEntity.ok().body(productDTO);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDTO>> getAllProductByName(@PathVariable String name){
        List<ProductDTO> productDTO = customerProductService.searchProductByTitle(name);
        return ResponseEntity.ok().body(productDTO);
    }


    @GetMapping("/FAQ/{productId}")
    public ResponseEntity<List<FAQDTO>> getFAQ(@PathVariable Long productId){
        List<FAQDTO> faqDTO = customerProductService.getFAQ(productId);
        return ResponseEntity.ok().body(faqDTO);
    }
}
