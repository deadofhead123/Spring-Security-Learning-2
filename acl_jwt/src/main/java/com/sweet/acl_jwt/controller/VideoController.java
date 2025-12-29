package com.sweet.acl_jwt.controller;

import com.sweet.acl_jwt.constant.RequestMatcherConst;
import com.sweet.acl_jwt.dto.ResponseDto;
import com.sweet.acl_jwt.dto.request.VideoRequest;
import com.sweet.acl_jwt.dto.request.grant.GrantRequest;
import com.sweet.acl_jwt.service.GrantService;
import com.sweet.acl_jwt.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RequestMatcherConst.API.VIDEO)
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    private final GrantService grantService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<?> createVideo(@RequestBody VideoRequest videoRequest) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setData(videoService.createVideo(videoRequest));
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @PreAuthorize("@abac.check('VIDEO', 'READ', {#id})")
    @GetMapping("/{id}")
    public ResponseEntity<?> watchVideo(@PathVariable Long id) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setData(videoService.watchVideo(id));
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    // Kiểm tra sao cho chỉ người tạo ra mới được sửa
    @PreAuthorize("@abac.check('VIDEO', 'UPDATE', {#id})")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVideo(@PathVariable Long id, @RequestBody VideoRequest videoRequest) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setData(videoService.updateVideo(id, videoRequest));
            responseDto.setMessage("Video updated successfully");
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    // Đang grant cho tất cả user được chọn (chưa check trường hợp user bị xóa)
    @PreAuthorize("@abac.check('VIDEO', 'GRANT', {#grantRequest.resourceId})")
    @PostMapping("/grant")
    public ResponseEntity<?> grantBlogForRead(@RequestBody GrantRequest grantRequest) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setData(grantService.createGrant(grantRequest));
            responseDto.setMessage("Blog granted for users with id = " + grantRequest.getUserGrantedIds().toString() + " successfully");
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @PreAuthorize("#ids != null && #ids.size() > 0 &&  @abac.check('VIDEO', 'DELETE', #ids)")
    @DeleteMapping("/{ids}")
    public ResponseEntity<?> deleteVideos(@PathVariable List<Long> ids) {
        ResponseDto responseDto = new ResponseDto();

        try{
            videoService.deleteVideos(ids);
            responseDto.setMessage("Blogs with id = "  + ids.toString() + " deleted successfully");
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }
}
