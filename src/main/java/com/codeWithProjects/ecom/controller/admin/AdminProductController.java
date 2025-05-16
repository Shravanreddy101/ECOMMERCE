package com.codeWithProjects.ecom.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeWithProjects.ecom.dto.FAQDTO;
import com.codeWithProjects.ecom.dto.ProductDTO;
import com.codeWithProjects.ecom.services.admin.FAQ.FAQService;
import com.codeWithProjects.ecom.services.admin.adminproduct.AdminProductService;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminProductController {

    @Autowired
    private final AdminProductService adminProductService;
    private final FAQService faqService;

    public AdminProductController(AdminProductService adminProductService, FAQService faqService){
        this.adminProductService = adminProductService;
        this.faqService = faqService;
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDTO> addProduct(@ModelAttribute ProductDTO productDTO) throws java.io.IOException{
        ProductDTO productDTO1 = adminProductService.addProduct(productDTO);
        return ResponseEntity.ok().body(productDTO1);
    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> findAll(){
        List<ProductDTO> productDTO = adminProductService.getAllProducts();
        return ResponseEntity.ok().body(productDTO);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
    ProductDTO result = adminProductService.findById(productId); // Pass only the ID
    if (result == null) {
        return ResponseEntity.notFound().build(); // Handle not found case
    }
    return ResponseEntity.ok().body(result); // Return the ProductDTO
}



    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDTO>> getAllProductByName(@PathVariable String name){
        List<ProductDTO> productDTO = adminProductService.getAllByNameContaining(name);
        return ResponseEntity.ok().body(productDTO);
    }


    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
        boolean deleted = adminProductService.deleteProduct(productId);
        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @PatchMapping("/updateProduct/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @ModelAttribute ProductDTO productDTO) throws java.io.IOException{
        ProductDTO productDTO1 = adminProductService.updateProduct(productId, productDTO);
        return ResponseEntity.ok().body(productDTO1);
    }


    @PostMapping("/postFAQ/{productId}")
    public ResponseEntity<FAQDTO> postFAQ(@PathVariable Long productId, @RequestBody FAQDTO faqDTO){
        return ResponseEntity.ok().body(faqService.postFAQ(productId, faqDTO));
    }



}
