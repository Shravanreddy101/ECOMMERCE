package com.codeWithProjects.ecom.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codeWithProjects.ecom.entity.Order;
import com.codeWithProjects.ecom.enums.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    List<Order> findAllByOrderStatusIn(List<OrderStatus> orderStatusList);

    List<Order> findAllByUser_NameContaining(String name);

    List<Order> findAllByOrderStatus(OrderStatus orderStatus);

    List<Order> findAllByUser_NameContainingAndOrderStatus(String name, OrderStatus orderStatus);

    List<Order> findAllByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    Optional<Order> findByTrackingId(UUID trackingId);

    List<Order> findByDateBetweenAndOrderStatus(Date startOfMonth, Date endOfMonth, OrderStatus status);

    Long countByOrderStatus(OrderStatus status);
}
