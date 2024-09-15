package meli.integrador.service;

import meli.integrador.dto.PostRequest;
import meli.integrador.dto.PostResponse;
import meli.integrador.exception.PostNotFoundException;
import meli.integrador.exception.ProductNotFound;
import meli.integrador.exception.UserNotFoundByIdException;
import meli.integrador.model.Post;
import meli.integrador.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPostByIdNotFound() {
        when(postRepository.getPostById(1L)).thenReturn(null);

        assertThrows(PostNotFoundException.class, () -> {
            postService.getPostById(1L);
        });

        verify(postRepository).getPostById(1L);
    }

    @Test
    public void testDeletePostByIdFound() throws PostNotFoundException {
        Post post = new Post();
        when(postRepository.getPostById(1L)).thenReturn(post);

        postService.deletePostById(1L);

        verify(postRepository).getPostById(1L);
        verify(postRepository).deleteById(1L);
    }

    @Test
    public void testDeletePostByIdNotFound() {
        when(postRepository.getPostById(1L)).thenReturn(null);

        assertThrows(PostNotFoundException.class, () -> {
            postService.deletePostById(1L);
        });

        verify(postRepository).getPostById(1L);
    }
}

