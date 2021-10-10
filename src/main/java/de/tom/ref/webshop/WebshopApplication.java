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
			String encodedPasswordDemoUser = passwordEncoder.encode("password");

			customerRepository.saveAll(initCustomers(encodedPassword, encodedPasswordDemoUser));
			productCategoryRepository.saveAll(productCategories);
			productRepository.saveAll(products);
		};
	}

	public static List<Customer> initCustomers(String encodedPassword, String encodedPasswordDemoUser) {
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer("Arnold Schwarzenegger", "arnold@test.de", encodedPassword,
				UserRole.ROLE_ADMIN, true, false));
		customers.add(new Customer("Jim Carry", "jim@test.de", encodedPassword,
				UserRole.ROLE_USER, true, false));
		customers.add(new Customer("John Doe", "john.doe@test.com", encodedPasswordDemoUser,
				UserRole.ROLE_USER, true, false));
		customers.add(new Customer("Jane Doe", "jane.doe@test.com", encodedPasswordDemoUser,
				UserRole.ROLE_USER, true, false));
		return customers;
	}

	public static List<ProductCategory> initProductCategories() {
		List<ProductCategory> productCategories = new ArrayList<>();
		productCategories.add(new ProductCategory("Raspberry Pi"));
		productCategories.add(new ProductCategory("Arduino"));
		productCategories.add(new ProductCategory("Cable & Cards"));
		productCategories.add(new ProductCategory("Sensors"));
		productCategories.add(new ProductCategory("Calliope"));
		return productCategories;
	}

	public static List<Product> initProducts(List<ProductCategory> productCategories) {
		List<Product> products = new ArrayList<>();

		/* Raspberry Pu Products */
		products.add(new Product("Raspberry Pi 3 Model B+",
				"Raspberry Pi 3 B+, 4x1,4GHz 1GB RAM, WLAN, BT",
				productCategories.get(0), new BigDecimal("38.50"), 10,
				"640px-Raspberry_Pi_3.png"));
		products.add(new Product("Raspberry Pi 4 Model B",
				"Raspberry Pi 4 B, 4x1,5GHz, 1 GB RAM, WLAN, BT",
				productCategories.get(0), new BigDecimal("45.50"), 3,
				"640px-Raspberry_Pi_4_Model_B.jpg"));
		products.add(new Product("Raspberry Pi Zero",
				"Raspberry Pi Zero, 4x1,5GHz, 1 GB RAM, WLAN, BT",
				productCategories.get(0), new BigDecimal("36.40"), 10,
				"640px-Raspberry-Pi-Zero.jpg"));
		products.add(new Product("Raspberry Pi  Embedded",
				"Raspberry Pi Nano Embedded Version, 4x1,5GHz, 1 GB RAM, WLAN, BT",
				productCategories.get(0), new BigDecimal("43.10"), 8,
				"640px-Embedded_Pi.jpg"));
		products.add(new Product("Raspberry Pi Compute Module",
				"Raspberry Pi Compute Module, 4x1,5GHz, 1 GB RAM, WLAN, BT",
				productCategories.get(0), new BigDecimal("47.10"), 8,
				"Raspberry_Pi_compute_module.jpg"));
		/*
		products.add(new Product("Raspberry Pi  Case",
				"Original Raspberry PI 3 Case, white red, for models 3B+, 3B, 2B, B+",
				productCategories.get(0), new BigDecimal("5.50"), 12,
				"640px-Raspberry_Pi_official_case.jpg"));
		products.add(new Product("Raspberry Pi  Keyboard",
				"Original Raspberry PI Keyboard, white and red, US layout, USB 2.0",
				productCategories.get(0), new BigDecimal("5.50"), 12,
				"640px-RaspberryPi_KeyBoardHub.jpg"));
		 */

		/* Arduino Products */
		products.add(new Product("Arduino UNO",
				"Arduino Uno Rev.3, ATmega328, USB, 5V, 14 digital pins, 6 analog outputs",
				productCategories.get(1), new BigDecimal("23.20"), 5,
				"627px-Arduino_Uno_r3.jpg"));
		products.add(new Product("Arduino Nano 33BLE H",
				"Arduino Nano 33 BLE Sense, with header, 3,3V 14 digital pins, 9 analog inputs",
				productCategories.get(1), new BigDecimal("25.50"), 5,
				"640px-Arduino_nano_bottm.jpg"));
		products.add(new Product("Arduino UNO Ethernet",
				"Arduino Uno Rev.3, ATmega328, USB, 5V, 14 digital pins, 6 analog outputs, " +
						"Ethernet Interface",
				productCategories.get(1), new BigDecimal("29.80"), 3,
				"640px-Arduino_Ethernet_Board.jpg"));
		products.add(new Product("Arduino Display",
				"Adafruit Arduino Display 160x80 pixel, monochrom",
				productCategories.get(1), new BigDecimal("17.25"), 5,
				"640px-Adafruit_PiTFT.jpg"));

		/* Calliope Mini Products */
		products.add(new Product("Calliope Mini",
				"High Retention HDMI cable 2,5m",
				productCategories.get(3), new BigDecimal("21.50"), 5,
				"480px-Calliope_mini.jpg"));
		products.add(new Product("Calliope Mini Starter Set",
				"High Retention HDMI cable 2,5m",
				productCategories.get(2), new BigDecimal("38.75"), 2,
				"540px-Calliope_mini_StarterSet.jpg"));

		/* Cables and Connectors */
		products.add(new Product("USB 3.0 Cable",
				"USB 3.0 cable, Micro B Stecker to A Stecker",
				productCategories.get(2), new BigDecimal("8.25"), 15,
				"514px-usb_cable.jpg"));
		products.add(new Product("HDMI Cable",
				"High Retention HDMI cable 2,5m",
				productCategories.get(2), new BigDecimal("10.25"), 15,
				"640px-HDMI_connector.jpg"));
		products.add(new Product("DVI HDMI Adapter",
				"High Retention HDMI cable 2,5m",
				productCategories.get(2), new BigDecimal("17.25"), 7,
				"640px-DVI-HDMI-Adapter.jpg"));

		/* Robotics and Sensors */
		products.add(new Product("Arduino Robot",
				"",
				productCategories.get(1), new BigDecimal("27.25"), 5,
				"Arduino_Robot_Top.jpg"));
		products.add(new Product("Pi Robot Kit",
				"Raspberry Pi Robot, 3 wheels",
				productCategories.get(1), new BigDecimal("57.25"), 5,
				"640px-Pi2Go-Lite_Kit.png"));
		products.add(new Product("Groove Distance Sensor",
				"Groove Distance Sensor",
				productCategories.get(1), new BigDecimal("11.45"), 5,
				"640px-GrovePi.png"));

		return products;
	}
}
