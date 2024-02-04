package dev.artsman.poc.product;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/products")
@RestController
class ProductController {
	@GetMapping
	ResponseEntity<List<Product>> findAll() {
		var m1 = new Product("M1", new BigDecimal(3_000));
		var iPhone = new Product("iPhone", new BigDecimal(2_000));
		var products = List.of(m1, iPhone);
		return ResponseEntity.ok(products);
	}
}
