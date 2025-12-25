package com.sweet.acl_jwt.controller;

import com.sweet.acl_jwt.constant.RequestMatcherConst;
import com.sweet.acl_jwt.dto.ResponseDto;
import com.sweet.acl_jwt.dto.request.PolicyRequest;
import com.sweet.acl_jwt.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RequestMatcherConst.API.POLICY)
@RequiredArgsConstructor
public class PolicyController {
    private final PolicyService policyService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPolicy(@RequestBody PolicyRequest policyRequest) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setData(policyService.createPolicy(policyRequest));
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }
}
