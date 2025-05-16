package com.codeWithProjects.ecom.services.customer.Orders;

import java.util.List;

import com.codeWithProjects.ecom.dto.OrderDTO;
import com.codeWithProjects.ecom.enums.OrderStatus;

public interface CustomerOrders {


    public List<OrderDTO> getAllOrders(Long userId);
}
