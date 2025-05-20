package com.codeWithProjects.ecom.services.admin.adminOrder;

import java.util.List;

import com.codeWithProjects.ecom.dto.AnalyticsResponseDTO;
import com.codeWithProjects.ecom.dto.OrderDTO;
import com.codeWithProjects.ecom.enums.OrderStatus;

public interface AdminOrderService {


    public List<OrderDTO> getAllPlacedOrders();

    public List<OrderDTO> getOrdersByUsername(String username);

    public List<OrderDTO> getOrdersByStatus(OrderStatus orderStatus);

    public List<OrderDTO> getOrdersByFilter(String name, OrderStatus orderStatus);

    public OrderDTO changeOrderStatus(Long orderId, String status);

    public AnalyticsResponseDTO calculateAnalytics();
}
