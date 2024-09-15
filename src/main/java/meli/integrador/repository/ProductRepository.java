package meli.integrador.repository;

import meli.integrador.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long>{

    @Query("SELECT p FROM Product p")
    Page<Product> findaAll(Pageable pageable);
}
