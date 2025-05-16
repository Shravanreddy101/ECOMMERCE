package com.codeWithProjects.ecom.services.customer.cart;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.codeWithProjects.ecom.dto.AddProductInCartDTO;
import com.codeWithProjects.ecom.dto.CartItemsDTO;
import com.codeWithProjects.ecom.dto.OrderDTO;
import com.codeWithProjects.ecom.dto.PlaceOrderDTO;
import com.codeWithProjects.ecom.entity.CartItems;
import com.codeWithProjects.ecom.entity.Coupon;
import com.codeWithProjects.ecom.entity.Order;
import com.codeWithProjects.ecom.entity.Product;
import com.codeWithProjects.ecom.entity.User;
import com.codeWithProjects.ecom.enums.OrderStatus;
import com.codeWithProjects.ecom.repository.CartItemsRepository;
import com.codeWithProjects.ecom.repository.CouponRepository;
import com.codeWithProjects.ecom.repository.OrderRepository;
import com.codeWithProjects.ecom.repository.ProductRepository;
import com.codeWithProjects.ecom.repository.UserRepository;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartItemsRepository cartItemsRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;

    public CartServiceImpl(OrderRepository orderRepository, UserRepository userRepository, CartItemsRepository cartItemsRepository, ProductRepository productRepository, CouponRepository couponRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartItemsRepository = cartItemsRepository;
        this.productRepository = productRepository;
        this.couponRepository = couponRepository;
    }


    @Override
    public CartItemsDTO addProductToCart(AddProductInCartDTO addProductInCartDTO) {

    // Validate User
    User user = userRepository.findById(addProductInCartDTO.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found."));

    // Validate Product
    Product product = productRepository.findById(addProductInCartDTO.getProductId())
        .orElseThrow(() -> new RuntimeException("Product not found."));
    product.getCategory();

    // Get or create order
    Order activeOrder = orderRepository.findByUserIdAndOrderStatus(user.getId(), OrderStatus.Pending);
    if (activeOrder == null) {
        activeOrder = new Order();
        activeOrder.setUser(user);
        activeOrder.setOrderStatus(OrderStatus.Pending);
        activeOrder.setTotalAmount(0L);
        activeOrder.setAmount(0L);
        activeOrder.setDiscount(0L);
        activeOrder = orderRepository.save(activeOrder);
    }

    Optional<CartItems> optionalCartItem = cartItemsRepository
        .findByProductIdAndOrderIdAndUserId(product.getId(), activeOrder.getId(), user.getId());

    CartItems cartItem;

    if (optionalCartItem.isPresent()) {
        cartItem = optionalCartItem.get();
        cartItem.setQuantity(cartItem.getQuantity() + 1);
    } else {
        cartItem = new CartItems();
        cartItem.setOrder(activeOrder);
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setPrice(product.getPrice());
        cartItem.setQuantity(1L);
    }

    cartItemsRepository.save(cartItem);

    activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cartItem.getPrice());
    activeOrder.setAmount(activeOrder.getAmount() + cartItem.getPrice());
    activeOrder.getCartItems().add(cartItem);
    orderRepository.save(activeOrder);

    return cartItem.getCartDTO();
}




    @Override
    public OrderDTO getCartByUserId(Long userId){
    Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId ,OrderStatus.Pending);
    List<CartItemsDTO> cartItemsDTOList = activeOrder.getCartItems().stream().map(CartItems::getCartDTO).collect(Collectors.toList());
    
    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setAmount(activeOrder.getAmount());
    orderDTO.setTotalAmount(activeOrder.getTotalAmount());
    orderDTO.setId(activeOrder.getId());
    orderDTO.setOrderStatus(activeOrder.getOrderStatus());
    orderDTO.setDiscount(activeOrder.getDiscount());
    orderDTO.setCartItems(cartItemsDTOList);
    
    if(activeOrder.getCoupon() != null){
        orderDTO.setCouponName(activeOrder.getCoupon().getName());
    }
    return orderDTO;

}





    @Override
    public OrderDTO applyCoupon(Long userId, String code){
        Order order = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(() -> new IllegalArgumentException("Coupon not found"));

        if(couponIsExpired(coupon)){
            throw new IllegalArgumentException("Coupon is expired");
        }

        double discountAmount = ((coupon.getDiscount() * 0.01) * order.getTotalAmount());
        double total = order.getTotalAmount() - discountAmount;

        order.setAmount((long) total);
        order.setDiscount((long)discountAmount);
        order.setCoupon(coupon);
        orderRepository.save(order);

        return order.getDTO();

    }

    private boolean couponIsExpired(Coupon coupon){
        Date currentDate = new Date();
        return coupon.getExpirationDate() != null && currentDate.after(coupon.getExpirationDate());
    }





    @Override
    public OrderDTO increaseProductQuantity(AddProductInCartDTO addProductInCartDTO){
        Optional<User> optionalUser = userRepository.findById(addProductInCartDTO.getUserId());
        User user = optionalUser.get();

        Order optionalOrder = orderRepository.findByUserIdAndOrderStatus(user.getId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDTO.getProductId());
        Product product = optionalProduct.get();

        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId(product.getId(), optionalOrder.getId(), user.getId());
        CartItems cartItems = optionalCartItems.get();

        
        cartItems.setQuantity(cartItems.getQuantity() + 1);
        optionalOrder.setAmount(optionalOrder.getAmount() + product.getPrice());
        optionalOrder.setTotalAmount(optionalOrder.getTotalAmount() + product.getPrice());

        
        if(optionalOrder.getCoupon() != null){
            double discountAmount = ((optionalOrder.getCoupon().getDiscount() * 0.01) * optionalOrder.getTotalAmount());
            double total = optionalOrder.getTotalAmount() - discountAmount;

            optionalOrder.setAmount((long) total);
            optionalOrder.setDiscount((long)discountAmount);
        }
        orderRepository.save(optionalOrder);
        cartItemsRepository.save(cartItems);

        return optionalOrder.getDTO();

    }

    @Override
    public OrderDTO decreaseProductQuantity(AddProductInCartDTO addProductInCartDTO){
        Optional<User> optionalUser = userRepository.findById(addProductInCartDTO.getUserId());
        User user = optionalUser.get();

        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDTO.getProductId());
        Product product = optionalProduct.get();

        Order order = orderRepository.findByUserIdAndOrderStatus(user.getId(), OrderStatus.Pending);

        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId(product.getId(), order.getId(), user.getId());
        CartItems cartItems = optionalCartItems.get();

        cartItems.setQuantity(cartItems.getQuantity() - 1);
        order.setAmount(Math.max(order.getAmount() - product.getPrice(),0));
        order.setTotalAmount(Math.max(order.getTotalAmount() - product.getPrice(),0));
        
        
        if(order.getCoupon() != null){
            double discountAmount = ((order.getCoupon().getDiscount() * 0.01) * order.getTotalAmount());
            double total = order.getTotalAmount() - discountAmount;

            order.setAmount((long) total);
            order.setDiscount((long)discountAmount);
        }

        orderRepository.save(order);

        if(cartItems.getQuantity() <= 0){
            cartItemsRepository.deleteById(cartItems.getId());
        }else{
            cartItemsRepository.save(cartItems);
        }

        return order.getDTO();
    }


    @Override
    public OrderDTO placeOrder(PlaceOrderDTO placeOrderDTO){
        Order order = orderRepository.findByUserIdAndOrderStatus(placeOrderDTO.getUserId(), OrderStatus.Pending);
        Optional<User> optionalUser = userRepository.findById(placeOrderDTO.getUserId());
        if(optionalUser.isPresent()){
            order.setAddress(placeOrderDTO.getAddress());
            order.setOrderDescription(placeOrderDTO.getOrderDescription());
            order.setDate(new Date());
            order.setOrderStatus(OrderStatus.Placed);
            order.setTrackingId(UUID.randomUUID());

            orderRepository.save(order);
            
            return order.getDTO();
        }
            return null;
    }



}
