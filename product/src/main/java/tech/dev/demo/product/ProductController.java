package tech.dev.demo.product;

import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/products")
@RestController
class ProductController {

	private List<Product> products = new ArrayList<>();

	public ProductController(List<Product> products) {
		var m1 = new Product("M1", new BigDecimal(3_000));
		var iPhone = new Product("iPhone", new BigDecimal(2_000));
		this.products = List.of(m1, iPhone);
	}

	@GetMapping
	ResponseEntity<List<Product>> findAll(@RequestParam @Nullable String name) {
		var filteredProducts = products;

        if (StringUtils.isNotBlank(name)) {
			filteredProducts = filteredProducts.stream()
					.filter(product -> product.name().equalsIgnoreCase(name)).toList();
		}

		return ResponseEntity.ok(filteredProducts);
	}
}
