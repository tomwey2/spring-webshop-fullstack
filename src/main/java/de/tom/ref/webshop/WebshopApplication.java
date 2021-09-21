package de.tom.ref.webshop;

import de.tom.ref.webshop.entities.products.Product;
import de.tom.ref.webshop.entities.products.ProductCategory;
import de.tom.ref.webshop.entities.products.ProductCategoryRepository;
import de.tom.ref.webshop.entities.products.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class WebshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebshopApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
		return args -> {
			List<ProductCategory> productCategories = initProductCategories();
			List<Product> products = initProducts(productCategories);

			productCategoryRepository.saveAll(productCategories);
			productRepository.saveAll(products);
		};
	}

	public static List<ProductCategory> initProductCategories() {
		List<ProductCategory> productCategories = new ArrayList<>();
		productCategories.add(new ProductCategory("Raspberry Pi"));
		productCategories.add(new ProductCategory("Arduino"));
		productCategories.add(new ProductCategory("Cable & Cards"));
		productCategories.add(new ProductCategory("Sensors"));
		return productCategories;
	}

	public static List<Product> initProducts(List<ProductCategory> productCategories) {
		List<Product> products = new ArrayList<>();
		products.add(new Product("Raspberry Pi 3 Model B+", productCategories.get(0), new BigDecimal("38.50"), 10));
		products.add(new Product("Raspberry Pi  power adapter", productCategories.get(0), new BigDecimal("6.40"), 10));
		products.add(new Product("Arduino UNO", productCategories.get(1), new BigDecimal("23.20"), 5));
		products.add(new Product("Arduino Nano", productCategories.get(1), new BigDecimal("25.50"), 5));
		products.add(new Product("Arduino Sensor Kit", productCategories.get(1), new BigDecimal("27.26"), 5));
		return products;
	}
}
