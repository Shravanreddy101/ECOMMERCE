package com.codeWithProjects.ecom.controller.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeWithProjects.ecom.dto.OrderDTO;
import com.codeWithProjects.ecom.services.customer.Orders.CustomerOrders;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerOrderController {


    @Autowired
    private final CustomerOrders customerOrders;
    

    public CustomerOrderController(CustomerOrders customerOrders){
        this.customerOrders = customerOrders;
    }


    @GetMapping("/orders/{userId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<OrderDTO>> getAllOrders(@PathVariable Long userId){
        List<OrderDTO> orderDTO = customerOrders.getAllOrders(userId);
        return ResponseEntity.ok().body(orderDTO);
    }


}
