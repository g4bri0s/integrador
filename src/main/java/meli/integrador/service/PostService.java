package meli.integrador.service;

import meli.integrador.dto.PostRequest;
import meli.integrador.dto.PostResponse;
import meli.integrador.exception.PostNotFoundException;
import meli.integrador.exception.ProductNotFound;
import meli.integrador.exception.UserNotFoundByIdException;
import meli.integrador.model.Post;
import meli.integrador.model.Product;
import meli.integrador.model.User;
import meli.integrador.repository.PostRepository;
import meli.integrador.repository.ProductRepository;
import meli.integrador.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public PostResponse createPost(PostRequest postRequest, Authentication auth) throws UserNotFoundByIdException, ProductNotFound {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(auth.getName()));
        Optional<Product> product = productRepository.findById(postRequest.getProduct());
        if (user.isEmpty()) {
            throw new UserNotFoundByIdException(auth.getName());
        }
        if (product.isEmpty()) {
            throw new ProductNotFound(postRequest.getProduct());
        }
        Post post = postRequest.toPost();
        post.setUser(user.get());
        post.setProduct(product.get());
        postRepository.create(post.getUser().getId(), post.getProduct().getId(), post.getCategory(), post.getPrice(), post.isHasPromo(), post.getDiscount());
        return new PostResponse(post);
    }

    public Page<PostResponse> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable).map(PostResponse::new);
    }

    public PostResponse getPostById(Long id) throws PostNotFoundException {
        Optional<Post> postOptional = Optional.ofNullable(postRepository.getPostById(id));
        if (postOptional.isPresent()) {
            return new PostResponse(postOptional.get());
        }
        throw new PostNotFoundException(id);
    }

    public PostResponse updatePostById(PostRequest postRequest, Long id, Authentication auth) throws PostNotFoundException, ProductNotFound, UserNotFoundByIdException {
        Optional<Post> postOptional = Optional.ofNullable(postRepository.getPostById(id));
        Optional<User>  user = auth.getName() != null ? Optional.ofNullable(userRepository.findByEmail(auth.getName())) : Optional.empty();
        Optional<Product> product = postRequest.getProduct() != null ? productRepository.findById(postRequest.getProduct()) : Optional.empty();
        if (product.isEmpty()) {
            throw new ProductNotFound(postRequest.getProduct());
        }
        if (user.isEmpty()) {
            throw new UserNotFoundByIdException(auth.getName());
        }
        if (postOptional.isPresent()) {
            Post post = postRequest.updatePost(postOptional.get());
            post.setUser(user.get());
            post.setProduct(product.get());
            postRepository.create(post.getUser().getId(), post.getProduct().getId(), post.getCategory(), post.getPrice(), post.isHasPromo(), post.getDiscount());
            return new PostResponse(post);
        }
        throw new PostNotFoundException(id);
    }

    public void deletePostById(Long id) throws PostNotFoundException {
        Optional<Post> postOptional = Optional.ofNullable(postRepository.getPostById(id));
        if (postOptional.isPresent()) {
            postRepository.deleteById(id);
            return;
        }
        throw new PostNotFoundException(id);
    }

    public Page<PostResponse> getPostsByUserId(String userId, int page,
                                             int size, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.getPostsFromFollowedUsers(userId, pageable,
          sortDirection).map(PostResponse::new);
    }
}