package de.tom.ref.webshop;

import de.tom.ref.webshop.entities.customers.Customer;
import de.tom.ref.webshop.entities.customers.CustomerRepository;
import de.tom.ref.webshop.entities.products.Product;
import de.tom.ref.webshop.entities.products.ProductCategory;
import de.tom.ref.webshop.entities.products.ProductCategoryRepository;
import de.tom.ref.webshop.entities.products.ProductRepository;
import de.tom.ref.webshop.enums.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class WebshopApplication {
	private final BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(WebshopApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(CustomerRepository customerRepository,
										ProductRepository productRepository,
										ProductCategoryRepository productCategoryRepository) {
		return args -> {
			List<ProductCategory> productCategories = initProductCategories();
			List<Product> products = initProducts(productCategories);
			String encodedPassword = passwordEncoder.encode("1234");

			customerRepository.saveAll(initCustomers(encodedPassword));
			productCategoryRepository.saveAll(productCategories);
			productRepository.saveAll(products);
		};
	}

	public static List<Customer> initCustomers(String encodedPassword) {
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer("Arnold Schwarzenegger", "arnold@test.de", encodedPassword,
				UserRole.ROLE_ADMIN, true, false));
		customers.add(new Customer("Will Smith", "will@test.de", encodedPassword,
				UserRole.ROLE_USER, true, false));
		customers.add(new Customer("Jim Carry", "jim@test.de", encodedPassword,
				UserRole.ROLE_USER, true, false));
		return customers;
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
