package com.codeWithProjects.ecom.services.auth;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.codeWithProjects.ecom.dto.SignupRequest;
import com.codeWithProjects.ecom.dto.UserDTO;
import com.codeWithProjects.ecom.entity.Order;
import com.codeWithProjects.ecom.entity.User;
import com.codeWithProjects.ecom.enums.OrderStatus;
import com.codeWithProjects.ecom.enums.UserRole;
import com.codeWithProjects.ecom.repository.OrderRepository;
import com.codeWithProjects.ecom.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private OrderRepository orderRepository;

    
    public AuthServiceImpl(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.orderRepository = orderRepository;
    }

    @Override
    public UserDTO createUser(SignupRequest signupRequest){
            User user = new User();
            user.setEmail(signupRequest.getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));
            user.setName(signupRequest.getName());
            user.setRole(UserRole.CUSTOMER);
            User createdUser = userRepository.save(user);

            Order order = new Order();
            order.setAmount(0L);
            order.setTotalAmount(0L);
            order.setDiscount(0L);
            order.setUser(createdUser);
            order.setOrderStatus(OrderStatus.Pending);
            orderRepository.save(order);

            UserDTO userDTO = new UserDTO();
            userDTO.setId(createdUser.getId());

            return userDTO;
    }

    @Override
    public boolean hasUserWithEmail(String email){
       return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminAccount(){
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if(null == adminAccount){
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(bCryptPasswordEncoder.encode("admin"));
            userRepository.save(user);
        }
    }   

}
