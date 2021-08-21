package com.rcdomingos.productms.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rcdomingos.productms.model.dto.ProductDTO;
import com.rcdomingos.productms.model.entities.Product;
import com.rcdomingos.productms.model.services.ProductService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductService service;

	@ApiOperation(value = "Lista de todos os produtos")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna a lista de produtos"),
	    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@GetMapping
	public ResponseEntity<List<Product>> findAllProducts() {
		List<Product> result = service.findAll();
		return ResponseEntity.ok().body(result);
	}

	
	@ApiOperation(value = "Criação de um produto")
	@ApiResponses(value = {
	    @ApiResponse(code = 201, message = "Produto cadastrada com sucesso"),
	    @ApiResponse(code = 400, message = "Requisição/Parametros inválido"),
	    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@PostMapping(produces="application/json", consumes="application/json")
	public ResponseEntity<Product> insertNewProduct(@Valid @RequestBody ProductDTO product) {
		Product result = service.insert(product.toProduct());
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(uri).body(result);
	}

	
	@ApiOperation(value = "Busca de um produto por ID")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna o produto"),
	    @ApiResponse(code = 404, message = "Recurso não encotrado"),
	    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findProductById(@PathVariable Long id) {
		Product result = service.findById(id);
		return ResponseEntity.ok().body(result);
	}

		
	@ApiOperation(value = "Atualização de um produto pelo ID")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna o produto alterado"),
	    @ApiResponse(code = 400, message = "Requisição/Parametros inválido"),
	    @ApiResponse(code = 404, message = "Recurso não encotrado"),
	    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@PutMapping(value = "/{id}", produces="application/json", consumes="application/json")
	public ResponseEntity<Product> updateProductById(@PathVariable Long id, @Valid @RequestBody ProductDTO product) {
		Product result = service.update(id, product.toProduct());
		return ResponseEntity.ok().body(result);
	}

	
	@ApiOperation(value = "Deleção de um produto pelo ID")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna o produto"),
	    @ApiResponse(code = 404, message = "Recurso não encotrado"),
	    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
		service.delete(id);
		// return ResponseEntity.noContent().build();
		return ResponseEntity.ok().build();
	}

	
	@ApiOperation(value = "Lista de produtos filtrados")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna a lista de produtos"),
	    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@GetMapping(value = "/search")
	public ResponseEntity<List<Product>> findAllProductsBySearch(
			@RequestParam(value = "q", defaultValue = "all") String q,
			@RequestParam(value = "min_price", defaultValue = "0") Double min_price,
			@RequestParam(value = "max_price", defaultValue = "99999999999999") Double max_price) {

		List<Product> result = service.search(q, min_price, max_price);
		return ResponseEntity.ok().body(result);
	}

}
