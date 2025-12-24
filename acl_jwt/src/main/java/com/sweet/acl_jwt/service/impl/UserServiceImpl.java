package com.sweet.acl_jwt.service.impl;

import com.sweet.acl_jwt.component.JwtUtil;
import com.sweet.acl_jwt.dto.request.LoginRequest;
import com.sweet.acl_jwt.dto.request.RegisterRequest;
import com.sweet.acl_jwt.dto.response.RegisterResponse;
import com.sweet.acl_jwt.entity.UserEntity;
import com.sweet.acl_jwt.repo.UserRepository;
import com.sweet.acl_jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;

    @Override
    public UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Override
    public String login(LoginRequest loginRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jwtUtil.generate(loginRequest.getUsername());
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        } catch (DisabledException e) {
            throw new DisabledException("Account is disabled");
        } catch (LockedException e) {
            throw new LockedException("Account is locked");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        if(userRepository.findByUsername(registerRequest.getUsername()) != null){
            throw new DataIntegrityViolationException("Username existed");
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        UserEntity savedUser = userRepository.save(newUser);

        if(savedUser != null){
            return modelMapper.map(savedUser, RegisterResponse.class);
        }
        return null;
    }
}
