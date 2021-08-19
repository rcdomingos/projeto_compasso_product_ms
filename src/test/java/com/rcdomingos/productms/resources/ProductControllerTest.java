package com.rcdomingos.productms.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.rcdomingos.productms.controller.ProductController;
import com.rcdomingos.productms.entities.Product;
import com.rcdomingos.productms.repositories.ProductRepository;
import com.rcdomingos.productms.services.ProductService;

@AutoConfigureJsonTesters
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ProductRepository repository;

	@Autowired
	private JacksonTester<Product> json;
	
	@MockBean
	private ProductService service;
	
	
	@Test
	public void should_create_new_product() throws Exception {
		MockHttpServletResponse response = mvc.perform(
					post("/products")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(
							json.write(new Product(42L, "Teclado", "Teclado novo", 59.90)).getJson()
						)
				)
				.andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

		ArgumentCaptor<Product> argCaptor = ArgumentCaptor.forClass(Product.class);
		verify(repository).save(argCaptor.capture());
		Product product = argCaptor.getValue();

		assertThat(product.getId()).isEqualTo(0);
		assertThat(product.getName()).isEqualTo("Marley");
		assertThat(product.getDescription()).isEqualTo("Teclado novo");
		assertThat(product.getPrice()).isEqualTo(59.90);
	}
	

	@Test
	public void should_return_existing_pet() throws Exception {
		given(repository.findById(42L)).willReturn(Optional.of(new Product(42L, "Teclado", "Teclado novo", 59.90)));

		MockHttpServletResponse response = mvc.perform(get("/products/42").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(json.write(new Product(42L, "Teclado", "Teclado novo", 59.90)).getJson());
	}

}
