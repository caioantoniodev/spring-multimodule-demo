package tech.dev.demo.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

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
