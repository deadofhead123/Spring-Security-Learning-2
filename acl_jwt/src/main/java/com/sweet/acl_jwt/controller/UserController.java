package com.sweet.acl_jwt.controller;

import com.sweet.acl_jwt.constant.RequestMatcherConst;
import com.sweet.acl_jwt.dto.ResponseDto;
import com.sweet.acl_jwt.dto.request.GrantRoleRequest;
import com.sweet.acl_jwt.dto.request.LoginRequest;
import com.sweet.acl_jwt.dto.request.RegisterRequest;
import com.sweet.acl_jwt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RequestMatcherConst.API.USER)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        ResponseDto responseDto = new ResponseDto();

        try {
            responseDto.setData(userService.login(loginRequest));
            responseDto.setMessage("Login successfully");
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(responseDto);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        ResponseDto responseDto = new ResponseDto();

        try {
            responseDto.setData(userService.register(registerRequest));
            responseDto.setMessage("successfully");
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/grant")
    public ResponseEntity<?> grantRole(@RequestBody GrantRoleRequest grantRoleRequest) {
        ResponseDto responseDto = new ResponseDto();

        try {
            userService.grantRole(grantRoleRequest);
            responseDto.setMessage("Grant role successfully");
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }
}
