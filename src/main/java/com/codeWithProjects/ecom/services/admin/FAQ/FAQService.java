package com.codeWithProjects.ecom.services.admin.FAQ;

import com.codeWithProjects.ecom.dto.FAQDTO;

public interface FAQService {

     public FAQDTO postFAQ(Long productId, FAQDTO faqDTO);
}
