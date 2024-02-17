package tech.dev.demo.product;

import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/products")
@RestController
class ProductController {

	private final List<Product> products = new ArrayList<>();

	public ProductController() {
		var m1 = new Product("M1", new BigDecimal(3_000));
		var iphone = new Product("Iphone", new BigDecimal(2_000));
		this.products.add(m1);
		this.products.add(iphone);
	}

	@GetMapping
	ResponseEntity<List<Product>> retrieveProducts(@RequestParam @Nullable String name,
										  @RequestParam @Nullable BigDecimal price) {
		var filteredProducts = this.products;

        if (StringUtils.isNotBlank(name)) {
			filteredProducts = filteredProducts.stream()
					.filter(product -> product.name().equalsIgnoreCase(name)).toList();
		}

		if (ObjectUtils.isNotEmpty(price)) {
			filteredProducts = filteredProducts.stream()
					.filter(product -> product.price().equals(price)).toList();
		}

		return ResponseEntity.ok(filteredProducts);
	}

	@PostMapping
	ResponseEntity<?> createProduct(@RequestBody Product product) {
		this.products.add(product);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("{name}")
	ResponseEntity<Boolean> removeProduct(@PathVariable String name) {
		return ResponseEntity.ok(products.removeIf(product -> product.name().equalsIgnoreCase(name)));
	}
}
