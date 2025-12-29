package com.sweet.acl_jwt.controller;

import com.sweet.acl_jwt.constant.RequestMatcherConst;
import com.sweet.acl_jwt.dto.PolicyUpdateRequest;
import com.sweet.acl_jwt.dto.ResponseDto;
import com.sweet.acl_jwt.dto.record.blog.BlogDeleteAttributes;
import com.sweet.acl_jwt.dto.request.PolicyRequest;
import com.sweet.acl_jwt.entity.UserEntity;
import com.sweet.acl_jwt.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RequestMatcherConst.API.POLICY)
@RequiredArgsConstructor
public class PolicyController {
    private final PolicyService policyService;
    private final ExpressionParser parser = new SpelExpressionParser();

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

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping
    public ResponseEntity<?> updatePolicy(@RequestBody PolicyUpdateRequest policyUpdateRequest) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setData(policyService.updatePolicy(policyUpdateRequest));
            responseDto.setMessage(String.format("Policy with id = %d updated successfully", policyUpdateRequest.getId()));
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test")
    public ResponseEntity<?> testPolicy(@RequestParam String expression) {
        ResponseDto responseDto = new ResponseDto();

        try{
            validate(expression);
            responseDto.setMessage("test policy successfully");
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    private void validate(String expression) {
        try {
            // Dummy data
            UserEntity user = dummyUser();
            Object resource = dummyResource();

            StandardEvaluationContext ctx = new StandardEvaluationContext();

            ctx.setVariable("user", user);
            ctx.setVariable("resource", resource);

            Boolean result = parser.parseExpression(expression).getValue(ctx, Boolean.class);

            if (result == null) {
                throw new IllegalArgumentException("Expression does not return boolean");
            }
        } catch (ParseException | EvaluationException ex) {
            throw new IllegalArgumentException("Invalid condition expression: " + ex.getMessage(), ex);
        }
    }

    private UserEntity dummyUser() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        return user;
    }

    private Object dummyResource() {
        return new BlogDeleteAttributes(
                9L
        );
    }
}
