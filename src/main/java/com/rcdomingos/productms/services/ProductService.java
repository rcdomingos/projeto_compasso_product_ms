package com.rcdomingos.productms.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.rcdomingos.productms.entities.Product;
import com.rcdomingos.productms.exceptions.DatabaseException;
import com.rcdomingos.productms.exceptions.ResourceNotFoundException;
import com.rcdomingos.productms.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public List<Product> findAll() {
		return repository.findAll();
	}

	public Product insert(Product product) {
		return repository.save(product);
	}

	public Product findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public Product update(Long id, Product obj) {
		try {
			Product entity = this.findById(id);
			updateDate(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}

	}

	private void updateDate(Product entity, Product product) {
		entity.setName(product.getName() == null ? entity.getName() : product.getName());
		entity.setDescription(product.getDescription() == null ? entity.getDescription() : product.getDescription());
		entity.setPrice(product.getPrice() == null ? entity.getPrice() : product.getPrice());
	}

	public List<Product> search(String q, Double min_price, Double max_price) {
		if (q.equals("all")) {
			return repository.search(min_price, max_price);
		} else {
			return repository.search(q.toLowerCase(), min_price, max_price);
		}
	}
}
