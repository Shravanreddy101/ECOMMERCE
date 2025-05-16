package com.codeWithProjects.ecom.services.auth;

import com.codeWithProjects.ecom.dto.SignupRequest;
import com.codeWithProjects.ecom.dto.UserDTO;

public interface AuthService {

public UserDTO createUser(SignupRequest signupRequest);

public boolean hasUserWithEmail(String email);

}
