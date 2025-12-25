package com.sweet.acl_jwt.controller;

import com.sweet.acl_jwt.constant.RequestMatcherConst;
import com.sweet.acl_jwt.constant.RoleEnum;
import com.sweet.acl_jwt.dto.BlogDto;
import com.sweet.acl_jwt.dto.ResponseDto;
import com.sweet.acl_jwt.dto.request.BlogRequest;
import com.sweet.acl_jwt.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(RequestMatcherConst.API.BLOG)
public class BlogController {
    private final BlogService blogService;
    private RoleEnum roleEnum;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<?> createBlog(@RequestBody BlogRequest blogRequest) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setData(blogService.createBlog(blogRequest));
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<?> readBlog(@RequestBody BlogRequest blogRequest) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setData(blogService.createBlog(blogRequest));
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    // Kiểm tra sao cho chỉ người tạo ra mới được sửa bài
    @PreAuthorize("@abac.check('BLOG', 'UPDATE', #id)")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable Long id, BlogDto blogDto) {
        ResponseDto responseDto = new ResponseDto();

        try{
//            responseDto.setData(blogService.createBlog(blogRequest));
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }
//
//    @DeleteMapping("/{id}")
//    public void del(@PathVariable Long id) {
//        repo.deleteById(id);
//    }
}
