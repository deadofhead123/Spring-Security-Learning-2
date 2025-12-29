package com.sweet.acl_jwt.controller;

import com.sweet.acl_jwt.constant.RequestMatcherConst;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RequestMatcherConst.API.VIDEO)
@RequiredArgsConstructor
public class VideoController {
//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
//    @PostMapping
//    public ResponseEntity<?> createBlog(@RequestBody BlogRequest blogRequest) {
//        ResponseDto responseDto = new ResponseDto();
//
//        try{
//            responseDto.setData(blogService.createBlog(blogRequest));
//            return ResponseEntity.ok(responseDto);
//        }
//        catch (Exception e){
//            responseDto.setMessage(e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
//        }
//    }
//
//    @PreAuthorize("@abac.check('BLOG', 'READ', {#id})")
//    @GetMapping("/{id}")
//    public ResponseEntity<?> readBlog(@PathVariable Long id) {
//        ResponseDto responseDto = new ResponseDto();
//
//        try{
//            responseDto.setData(blogService.readBlog(id));
//            return ResponseEntity.ok(responseDto);
//        }
//        catch (Exception e){
//            responseDto.setMessage(e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
//        }
//    }
//
//    // Kiểm tra sao cho chỉ người tạo ra mới được sửa bài
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN') && @abac.check('BLOG', 'UPDATE', {#id})")
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateBlog(@PathVariable Long id, @RequestBody BlogRequest blogRequest) {
//        ResponseDto responseDto = new ResponseDto();
//
//        try{
//            responseDto.setData(blogService.updateBlog(id, blogRequest));
//            responseDto.setMessage("Blog updated successfully");
//            return ResponseEntity.ok(responseDto);
//        }
//        catch (Exception e){
//            responseDto.setMessage(e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
//        }
//    }
//
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN') && @abac.check('BLOG', 'GRANT', {#grantRequest.resourceId})")
//    @PostMapping("/grant")
//    public ResponseEntity<?> grantBlogForRead(@RequestBody GrantRequest grantRequest) {
//        ResponseDto responseDto = new ResponseDto();
//
//        try{
//            responseDto.setData(grantService.createGrant(grantRequest));
//            responseDto.setMessage("Blog granted for user with id = " + grantRequest.getUserGrantedId() + " successfully");
//            return ResponseEntity.ok(responseDto);
//        }
//        catch (Exception e){
//            responseDto.setMessage(e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
//        }
//    }
//
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN') && #ids != null &&  @abac.check('BLOG', 'DELETE', #ids)")
//    @DeleteMapping("/{ids}")
//    public ResponseEntity<?> deleteBlog(@PathVariable List<Long> ids) {
//        ResponseDto responseDto = new ResponseDto();
//
//        try{
//            blogService.deleteByIds(ids);
//            responseDto.setMessage("Blogs deleted successfully");
//            return ResponseEntity.ok(responseDto);
//        }
//        catch (Exception e){
//            responseDto.setMessage(e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
//        }
//    }
}
