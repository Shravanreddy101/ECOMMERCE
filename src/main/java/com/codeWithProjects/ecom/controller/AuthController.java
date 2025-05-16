package com.codeWithProjects.ecom.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.codeWithProjects.ecom.dto.AuthenticationRequest;
import com.codeWithProjects.ecom.dto.SignupRequest;
import com.codeWithProjects.ecom.dto.UserDTO;
import com.codeWithProjects.ecom.entity.User;
import com.codeWithProjects.ecom.repository.UserRepository;
import com.codeWithProjects.ecom.services.auth.AuthService;
import com.codeWithProjects.ecom.utils.JwtUtil;





@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthService authService;
    

    public AuthController(AuthenticationManager authenticationManager,UserDetailsService userDetailsService,UserRepository userRepository,JwtUtil jwtUtil, AuthService authService){
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<Map<String,Object>> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
            
            // Load UserDetails
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            
            // Extract roles and generate token
            final String jwt = jwtUtil.generateToken(authentication);  // Pass Authentication instead of just username
            
            // Fetch user details to include in the response
            Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());

            if (optionalUser.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("jwt", jwt);
                response.put("userId", optionalUser.get().getId());
                response.put("role", optionalUser.get().getRole());
                return ResponseEntity.ok(response);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        if(authService.hasUserWithEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDTO userDTO = authService.createUser(signupRequest);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

}
