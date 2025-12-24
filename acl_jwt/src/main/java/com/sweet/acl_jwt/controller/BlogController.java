package com.sweet.acl_jwt.controller;

import com.sweet.acl_jwt.dto.ResponseDto;
import com.sweet.acl_jwt.dto.request.BlogRequest;
import com.sweet.acl_jwt.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blogs")
public class BlogController {
    private final BlogService blogService;

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

//    @GetMapping
//    public List<PostEntity> all() {
//        return repo.findAll();
//    }
//
//    @PutMapping("/{id}")
//    public PostEntity update(@PathVariable Long id, @RequestBody PostEntity p) {
//        PostEntity db = repo.findById(id).orElseThrow();
//        db.setTitle(p.getTitle());
//        db.setContent(p.getContent());
//        return repo.save(db);
//    }
//
//    @DeleteMapping("/{id}")
//    public void del(@PathVariable Long id) {
//        repo.deleteById(id);
//    }
}
