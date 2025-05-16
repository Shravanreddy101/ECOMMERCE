package com.codeWithProjects.ecom.services.admin.FAQ;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeWithProjects.ecom.dto.FAQDTO;
import com.codeWithProjects.ecom.entity.FAQ;
import com.codeWithProjects.ecom.entity.Product;
import com.codeWithProjects.ecom.repository.FAQRepository;
import com.codeWithProjects.ecom.repository.ProductRepository;

@Service
public class FAQServiceImpl implements FAQService {

    @Autowired
    private final FAQRepository faqRepository;
    private final ProductRepository productRepository;



    public FAQServiceImpl(FAQRepository faqRepository, ProductRepository productRepository){
        this.faqRepository = faqRepository;
        this.productRepository = productRepository;
    }


    @Override
    public FAQDTO postFAQ(Long productId, FAQDTO faqDTO){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            FAQ faq = new FAQ();
            faq.setProduct(product);
            faq.setQuestion(faqDTO.getQuestion());
            faq.setAnswer(faqDTO.getAnswer());

            faqRepository.save(faq);
            return faq.getDTO();
        }
        return null;
    }
}
