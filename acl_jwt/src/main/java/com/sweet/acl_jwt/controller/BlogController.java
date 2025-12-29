package com.sweet.acl_jwt.controller;

import com.sweet.acl_jwt.constant.RequestMatcherConst;
import com.sweet.acl_jwt.dto.ResponseDto;
import com.sweet.acl_jwt.dto.request.blog.BlogRequest;
import com.sweet.acl_jwt.dto.request.grant.GrantRequest;
import com.sweet.acl_jwt.enumeration.RoleEnum;
import com.sweet.acl_jwt.service.BlogService;
import com.sweet.acl_jwt.service.GrantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(RequestMatcherConst.API.BLOG)
public class BlogController {
    private final BlogService blogService;
    private final GrantService grantService;
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

    @PreAuthorize("@abac.check('BLOG', 'READ', {#id})")
    @GetMapping("/{id}")
    public ResponseEntity<?> readBlog(@PathVariable Long id) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setData(blogService.readBlog(id));
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    // Kiểm tra sao cho chỉ người tạo ra mới được sửa bài
    @PreAuthorize("@abac.check('BLOG', 'UPDATE', {#id})")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable Long id, @RequestBody BlogRequest blogRequest) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setData(blogService.updateBlog(id, blogRequest));
            responseDto.setMessage("Blog updated successfully");
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @PreAuthorize("@abac.check('BLOG', 'GRANT', {#grantRequest.resourceId})")
    @PostMapping("/grant")
    public ResponseEntity<?> grantBlogForRead(@RequestBody GrantRequest grantRequest) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setData(grantService.createGrant(grantRequest));
            responseDto.setMessage("Blog granted for user with id = " + grantRequest.getUserGrantedIds().toString() + " successfully");
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @PreAuthorize("#ids != null &&  @abac.check('BLOG', 'DELETE', #ids)")
    @DeleteMapping("/{ids}")
    public ResponseEntity<?> deleteBlog(@PathVariable List<Long> ids) {
        ResponseDto responseDto = new ResponseDto();

        try{
            blogService.deleteByIds(ids);
            responseDto.setMessage("Blogs deleted successfully");
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }
}
