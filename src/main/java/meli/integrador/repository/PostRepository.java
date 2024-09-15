package meli.integrador.repository;

import meli.integrador.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p")
    Page<Post> findAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO posts (date, user_id, product_id, category, price, has_promo, discount) VALUES (NOW(), :user, :product, :category, :price, :has_promo, :discount)", nativeQuery = true)
    void create(@Param("user") String user, @Param("product") Long product, @Param("category") int category, @Param("price") BigDecimal price, @Param("has_promo") boolean has_promo, @Param("discount") BigDecimal discount);

    @Transactional
    @Query(value = "SELECT * FROM posts p WHERE p.id = :id", nativeQuery = true)
    Post getPostById(@Param("id") Long id);

    @Transactional
    @Query(value = "SELECT * FROM posts p WHERE p.user_id IN (SELECT f" +
      ".id_followed FROM followers f WHERE f.id_follower = :id_follower) AND " +
      "p.date >= DATE_SUB(NOW(), INTERVAL 2 WEEK) ORDER BY p.date [:param]",
      nativeQuery = true)
    Page<Post> getPostsFromFollowedUsers(@Param("id_follower") String id_follower, Pageable pageable, @Param("param") String date);
}