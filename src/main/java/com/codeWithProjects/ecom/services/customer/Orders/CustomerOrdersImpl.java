package com.codeWithProjects.ecom.services.customer.Orders;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeWithProjects.ecom.dto.OrderDTO;
import com.codeWithProjects.ecom.entity.Order;
import com.codeWithProjects.ecom.enums.OrderStatus;
import com.codeWithProjects.ecom.repository.OrderRepository;

@Service
public class CustomerOrdersImpl implements CustomerOrders{

    @Autowired
    private final OrderRepository orderRepository;

    public CustomerOrdersImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDTO> getAllOrders(Long userId){
        List<Order> orders = orderRepository.findAllByUserIdAndOrderStatus(userId, OrderStatus.Placed);
        return orders.stream().map(Order::getDTO).collect(Collectors.toList());
    }

}
