package com.codeWithProjects.ecom.services.admin.adminOrder;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeWithProjects.ecom.dto.AnalyticsResponseDTO;
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



    @Override
    public AnalyticsResponseDTO calculateAnalytics(){
        LocalDate currentDate = LocalDate.now();
        LocalDate previousMonthDate = currentDate.minusMonths(1);

        Long currentMonthOrders = getTotalOrdersForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthOrders = getTotalOrdersForMonth(previousMonthDate.getMonthValue(), previousMonthDate.getYear());

        Long currentMonthEarnings = getTotalEarningsForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthEarnings = getTotalEarningsForMonth(previousMonthDate.getMonthValue(), previousMonthDate.getYear());

        Long placed = orderRepository.countByOrderStatus(OrderStatus.Placed);
        Long shipped = orderRepository.countByOrderStatus(OrderStatus.Shipped);
        Long delivered = orderRepository.countByOrderStatus(OrderStatus.Delivered);

        return new AnalyticsResponseDTO(placed, shipped, delivered, currentMonthOrders, previousMonthOrders, currentMonthEarnings, previousMonthEarnings);
    }


    public Long getTotalOrdersForMonth(int month, int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        Date startOfMonth = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);

        Date endOfMonth = calendar.getTime();
        List<Order> orders = orderRepository.findByDateBetweenAndOrderStatus(startOfMonth, endOfMonth, OrderStatus.Delivered);

        return (long)orders.size();
    }

    public Long getTotalEarningsForMonth(int month, int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        Date startOfMonth = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);

        Date endOfMonth = calendar.getTime();
        List<Order> orders = orderRepository.findByDateBetweenAndOrderStatus(startOfMonth, endOfMonth, OrderStatus.Delivered);

        Long sum = 0L;
        for(Order order: orders){
            sum+= order.getAmount();
        }
        return sum;
    }

}
