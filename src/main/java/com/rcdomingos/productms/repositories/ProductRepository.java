package com.rcdomingos.productms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rcdomingos.productms.model.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("FROM Product p WHERE (p.price >= :min_price AND p.price <= :max_price) "
			+ " AND LOWER(p.name) like %:searchTerm% OR LOWER(p.description) like %:searchTerm%")
	List<Product> search(@Param("searchTerm") String searchTerm, @Param("min_price") Double min_price,
			@Param("max_price") Double max_price);
	
	
	@Query("FROM Product p WHERE (p.price >= :min_price AND p.price <= :max_price) ")
	List<Product> search(@Param("min_price") Double min_price, @Param("max_price") Double max_price);

}
