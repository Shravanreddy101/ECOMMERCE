package com.codeWithProjects.ecom.services.admin.adminOrder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeWithProjects.ecom.dto.OrderDTO;
import com.codeWithProjects.ecom.entity.Order;
import com.codeWithProjects.ecom.enums.OrderStatus;
import com.codeWithProjects.ecom.repository.OrderRepository;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    @Autowired
    private final OrderRepository orderRepository;

    public AdminOrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDTO> getAllPlacedOrders(){
        List<Order> orderList = orderRepository.findAllByOrderStatusIn(List.of(OrderStatus.Placed, OrderStatus.Shipped, OrderStatus.Delivered));
        return orderList.stream().map(Order::getDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByUsername(String name){
        List<Order> orderList = orderRepository.findAllByUser_NameContaining(name);
        return orderList.stream().map(Order::getDTO).collect(Collectors.toList());
    }


    @Override
    public List<OrderDTO> getOrdersByStatus(OrderStatus orderStatus){
        List<Order> orderList = orderRepository.findAllByOrderStatus(orderStatus);
        return orderList.stream().map(Order::getDTO).collect(Collectors.toList());
    }


    @Override
    public List<OrderDTO> getOrdersByFilter(String name, OrderStatus orderStatus){
        List<Order> orders;

        boolean hasUsername = name !=null && !name.isBlank();
        boolean hasStatus = orderStatus != null;

        if(hasUsername && hasStatus){
            orders = orderRepository.findAllByUser_NameContainingAndOrderStatus(name, orderStatus);
        }
        else if(hasUsername){
            orders = orderRepository.findAllByUser_NameContaining(name);
        }
        else if(hasStatus){
            orders = orderRepository.findAllByOrderStatus(orderStatus);
        }
        else{
            orders = orderRepository.findAll();
        }

        return orders.stream().map(Order::getDTO).collect(Collectors.toList());
    }


    @Override
    public OrderDTO changeOrderStatus(Long orderId, String status){
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            if(Objects.equals(status, "Shipped")){
                order.setOrderStatus(OrderStatus.Shipped);
            }
            else if(Objects.equals(status, "Delivered")){
                order.setOrderStatus(OrderStatus.Delivered);
            }
            return orderRepository.save(order).getDTO();
        }
        return null;
    }

}
