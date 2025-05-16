package com.codeWithProjects.ecom.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codeWithProjects.ecom.dto.OrderDTO;
import com.codeWithProjects.ecom.enums.OrderStatus;
import com.codeWithProjects.ecom.services.admin.adminOrder.AdminOrderService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminOrderController {


    @Autowired
    private final AdminOrderService adminOrderService;

    public AdminOrderController(AdminOrderService adminOrderService){
        this.adminOrderService = adminOrderService;
    }


    @GetMapping("/placedOrders")
    public ResponseEntity<List<OrderDTO>> getAllPlacedOrders(){
        return ResponseEntity.ok().body(adminOrderService.getAllPlacedOrders());
    }


    @GetMapping("/orders/{username}")
    public ResponseEntity<List<OrderDTO>> getAllOrdersByUsername(@PathVariable String username){
        List<OrderDTO> orderDTO = adminOrderService.getOrdersByUsername(username);
        return ResponseEntity.ok().body(orderDTO);
    }


    @GetMapping("/orders/status/{status}")
    public ResponseEntity<List<OrderDTO>> getAllOrdersByStatus(@PathVariable String status){
        OrderStatus orderStatus = OrderStatus.valueOf(status);
        List<OrderDTO> orderDTO = adminOrderService.getOrdersByStatus(orderStatus);
        return ResponseEntity.ok().body(orderDTO);
    }


    @GetMapping("/orders/filter")
    public ResponseEntity<List<OrderDTO>> getOrdersByFilter(@RequestParam(required = false) String name, @RequestParam(required = false) String status){
        OrderStatus orderStatus = null;
        if(status != null && !status.isBlank()){
            try{
                orderStatus = OrderStatus.valueOf(status);
            }
            catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().build();
            }
        }
        List<OrderDTO> orderDTO = adminOrderService.getOrdersByFilter(name, orderStatus);
        return ResponseEntity.ok().body(orderDTO);
    }

    @PatchMapping("/orders/{orderId}/{status}")
    public ResponseEntity<OrderDTO> changeOrderStatus(@PathVariable Long orderId, @PathVariable String status){
        OrderDTO orderDTO = adminOrderService.changeOrderStatus(orderId, status);
        return ResponseEntity.ok().body(orderDTO);
    }
}
