package meli.integrador.repository;

import meli.integrador.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>{

    @Query("SELECT u FROM User u")
    Page<User> findAll(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

    @Transactional
    @Query(value = "SELECT * FROM users u WHERE u.cpf = :cpf OR u.email = :email", nativeQuery = true)
    Optional<User> findByCpfOrEmail(@Param("cpf") String cpf, @Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO followers (id_follower, id_followed) VALUES (:idFollower, :idFollowed)", nativeQuery = true)
    void followUser(@Param("idFollower") String idFollower, @Param("idFollowed") String idFollowed);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM followers WHERE id_follower = :idFollower AND id_followed = :idFollowed", nativeQuery = true)
    int unfollowUser(@Param("idFollower") String idFollower, @Param("idFollowed") String idFollowed);

    @Transactional
    @Query(value = "SELECT * FROM followers f JOIN users u ON f.id_follower = u.user_id WHERE f.id_followed = :idFollower", nativeQuery = true)
    Page<User> getFollowers(@Param("idFollower") String idFollower, Pageable pageable);

    @Transactional
    @Query(value = "SELECT COUNT(id_followed) FROM followers AS f WHERE f.id_followed = :id", nativeQuery = true)
    int countFollowers(@Param("id") String id);

    @Transactional
    @Query(value = "SELECT COUNT(id_follower) FROM followers AS f WHERE f.id_follower = :id AND f.id_followed = :sellerId", nativeQuery = true)
    int isFollowing(@Param("id") String id, @Param("sellerId") String sellerId);
}
