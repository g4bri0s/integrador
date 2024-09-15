package meli.integrador.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import meli.integrador.dto.PostRequest;
import meli.integrador.dto.PostResponse;
import meli.integrador.exception.PostNotFoundException;
import meli.integrador.exception.ProductNotFound;
import meli.integrador.exception.UserNotFoundByIdException;
import meli.integrador.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public PostResponse createPost(@Valid @RequestBody PostRequest postRequest, Authentication auth) throws ProductNotFound, UserNotFoundByIdException {
        return postService.createPost(postRequest, auth);
    }

    @GetMapping
    public Page<PostResponse> getPosts(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        return postService.getPosts(page, size);
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) throws PostNotFoundException {
        return postService.getPostById(id);
    }

    @PutMapping("/{id}")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public PostResponse updatePost(@PathVariable Long id, @Valid @RequestBody PostRequest postRequest, Authentication auth) throws PostNotFoundException, ProductNotFound, UserNotFoundByIdException {
        return postService.updatePostById(postRequest, id, auth);
    }

    @DeleteMapping("/{id}")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public void deletePost(@PathVariable Long id) throws PostNotFoundException {
        postService.deletePostById(id);
    }

    @GetMapping("/followed/{userId}/list")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public Page<PostResponse> getPostsByUserId(@PathVariable String userId,
                                               @RequestParam(defaultValue = "0") Integer page,
                                               @RequestParam(defaultValue = "10") Integer size,
                                               @RequestParam(defaultValue = "DESC") String sortDirection){
        return postService.getPostsByUserId(userId, page, size, sortDirection);
    }
}
