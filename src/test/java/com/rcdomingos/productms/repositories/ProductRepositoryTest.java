package com.rcdomingos.productms.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.rcdomingos.productms.entities.Product;

@DataJpaTest
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@Test
	@DisplayName("Deve Cadastrar um novo produto no banco de dados")
	public void should_create_new_product() {

		Product product = new Product("Computador", "Computador para desenv", 4569.90);

		productRepository.save(product);

		System.out.println(product.getId());

		assertThat(product.getId()).isNotNull();
		assertThat(product.getName()).isEqualTo("Computador");
		assertThat(product.getDescription()).isEqualTo("Computador para desenv");
		assertThat(product.getPrice()).isEqualTo(4569.90);
	}

	@Test
	@DisplayName("Deve achar todos os produtos cadastrados")
	public void should_find_all_products() {
		Product product1 = new Product("PC1", "teste pc1", 999.00);
		Product product2 = new Product("PC2", "teste pc1", 999.00);
		Product product3 = new Product("PC3", "teste pc1", 999.00);

		productRepository.saveAll(List.of(product1, product2, product3));

		Iterable<Product> findAllResult = productRepository.findAll();

		assertThat(findAllResult).hasSize(3);
	}

	@Test
	@DisplayName("Deve deletar um produto pelo id")
	public void should_delete_product_by_id() {
		Product product = new Product("PC1", "teste pc1", 999.00);
		productRepository.save(product);

		productRepository.delete(product);

		assertThat(productRepository.findById(product.getId())).isEmpty();
	}

}
