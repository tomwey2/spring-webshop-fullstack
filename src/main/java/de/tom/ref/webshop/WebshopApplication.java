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

		boolean localimg = false;

		/* Raspberry Pu Products */
		products.add(new Product("Raspberry Pi 3 Model B+",
				"Raspberry Pi 3 B+, 4x1,4GHz 1GB RAM, WLAN, BT",
				productCategories.get(0), new BigDecimal("38.50"), 10,
				localimg ? "img/640px-Raspberry_Pi_3.png" :
				"https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Raspberry_Pi_3_B%2B_%2826931247748%29.png/640px-Raspberry_Pi_3_B%2B_%2826931247748%29.png",
				""));
		products.add(new Product("Raspberry Pi 4 Model B",
				"Raspberry Pi 4 B, 4x1,5GHz, 1 GB RAM, WLAN, BT",
				productCategories.get(0), new BigDecimal("45.50"), 3,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/f/f1/Raspberry_Pi_4_Model_B_-_Side.jpg/640px-Raspberry_Pi_4_Model_B_-_Side.jpg",
				""));
		products.add(new Product("Raspberry Pi Zero",
				"Raspberry Pi Zero, 4x1,5GHz, 1 GB RAM, WLAN, BT",
				productCategories.get(0), new BigDecimal("36.40"), 10,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/b/bf/Raspberry_Pi_Zero_%2823317579575%29.png/640px-Raspberry_Pi_Zero_%2823317579575%29.png",
				""));
		products.add(new Product("Raspberry Pi Pico",
				"Raspberry Pi Pico , 4x1,5GHz, 1 GB RAM, WLAN, BT",
				productCategories.get(0), new BigDecimal("36.40"), 10,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/7/7b/Raspberry_pi_pico_oben.jpg/640px-Raspberry_pi_pico_oben.jpg",
				""));
		products.add(new Product("Raspberry Pi  Embedded",
				"Raspberry Pi Nano Embedded Version, 4x1,5GHz, 1 GB RAM, WLAN, BT",
				productCategories.get(0), new BigDecimal("43.10"), 8,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/0/02/Embedded_Pi_-_Top_%2814490989150%29.jpg/640px-Embedded_Pi_-_Top_%2814490989150%29.jpg",
				""));
		products.add(new Product("Raspberry Pi Compute Module",
				"Raspberry Pi Compute Module, 4x1,5GHz, 1 GB RAM, WLAN, BT",
				productCategories.get(0), new BigDecimal("47.10"), 8,
				"https://upload.wikimedia.org/wikipedia/commons/7/7a/CM_4_TOP_DOWN_ON_WHITE.jpg", ""));
		products.add(new Product("Banana Pi",
				"Banana Pi 3 B+, 4x1,4GHz 1GB RAM, WLAN, BT",
				productCategories.get(0), new BigDecimal("38.50"), 10,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/Banana_Pi_-_Top_%2814673315124%29.png/640px-Banana_Pi_-_Top_%2814673315124%29.png",
				""));

		/* Arduino Products */
		products.add(new Product("Arduino UNO",
				"Arduino Uno Rev.3, ATmega328, USB, 5V, 14 digital pins, 6 analog outputs",
				productCategories.get(1), new BigDecimal("23.20"), 5,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/a/a6/Arduino_Uno_006.jpg/640px-Arduino_Uno_006.jpg",
				""));
		products.add(new Product("Arduino Nano 33BLE H",
				"Arduino Nano 33 BLE Sense, with header, 3,3V 14 digital pins, 9 analog inputs",
				productCategories.get(1), new BigDecimal("25.50"), 5,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/e/e5/Arduino_nano_bottm.jpg/640px-Arduino_nano_bottm.jpg",
				""));
		products.add(new Product("Arduino Nano Pico RP2040",
				"Arduino Nano Pico with RP2040, with header, 3,3V 14 digital pins, 9 analog inputs",
				productCategories.get(1), new BigDecimal("25.50"), 5,
				"https://upload.wikimedia.org/wikipedia/commons/1/1a/18555_-_Arduino_Nano_RP2040_Connect.jpg",
				""));
		products.add(new Product("Arduino UNO Ethernet",
				"Arduino Uno Rev.3, ATmega328, USB, 5V, 14 digital pins, 6 analog outputs, " +
						"Ethernet Interface",
				productCategories.get(1), new BigDecimal("29.80"), 3,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/1/16/Arduino_Ethernet_Board.jpg/640px-Arduino_Ethernet_Board.jpg",
				""));

		/* Calliope Mini and Microbit Products */
		products.add(new Product("Calliope Mini",
				"High Retention HDMI cable 2,5m",
				productCategories.get(3), new BigDecimal("21.50"), 5,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/6/6f/Calliope_mini_weiss_JoernAlraun.jpg/480px-Calliope_mini_weiss_JoernAlraun.jpg",
				""));
		products.add(new Product("Calliope Mini Starter Set",
				"High Retention HDMI cable 2,5m",
				productCategories.get(2), new BigDecimal("38.75"), 2,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/Calliope_mini_StarterSet_komplett_Front_JoernAlraun.jpg/576px-Calliope_mini_StarterSet_komplett_Front_JoernAlraun.jpg",
				""));
		products.add(new Product("Microbit",
				"Microbit",
				productCategories.get(2), new BigDecimal("14.90"), 2,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/f/f2/BBC_micro_bit_%2826212930836%29.png/640px-BBC_micro_bit_%2826212930836%29.png",
				""));
		products.add(new Product("Microbit Kitronik Kit",
				"Kitronik inventor's kit for the BBC micro:bit",
				productCategories.get(2), new BigDecimal("28.80"), 2,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/6/63/Kitronik_micro_bit_Inventor%27s_Kit_%2832952992662%29.png/640px-Kitronik_micro_bit_Inventor%27s_Kit_%2832952992662%29.png",
				""));
		products.add(new Product("Kitronik GameZip",
				"Retro gaming accessory for the BBC microbit.",
				productCategories.get(2), new BigDecimal("42.80"), 2,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Kitronik_GameZip_64_%2844466554265%29.png/640px-Kitronik_GameZip_64_%2844466554265%29.png",
				""));

		/* Robotics and Sensors */
		products.add(new Product("Arduino Robot",
				"",
				productCategories.get(1), new BigDecimal("27.25"), 5,
				"https://upload.wikimedia.org/wikipedia/commons/4/48/Arduino_Robot_Bottom1.jpg",
				""));
		products.add(new Product("Pi Robot Kit",
				"Raspberry Pi Robot, 3 wheels",
				productCategories.get(1), new BigDecimal("57.25"), 5,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Pi2Go-Lite_Kit_-_Assembled_%2815038423411%29.png/640px-Pi2Go-Lite_Kit_-_Assembled_%2815038423411%29.png",
				""));
		products.add(new Product("Groove Distance Sensor",
				"Groove Distance Sensor",
				productCategories.get(1), new BigDecimal("11.45"), 5,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/e/e3/GrovePi%2B_Starter_Kit_%2816444233434%29.png/640px-GrovePi%2B_Starter_Kit_%2816444233434%29.png",
				""));
		products.add(new Product("GiggleBot Starter Kit",
				"GiggleBot Starter Kit includes a GiggleBot robot chassis. " +
						"The micro:bit is not include",
				productCategories.get(1), new BigDecimal("79.95"), 5,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/7/7b/GiggleBot_%2845176510742%29.png/640px-GiggleBot_%2845176510742%29.png",
				""));
		products.add(new Product("Pi2Go Lite Kit",
				"Pi 2 Go Lite Kit " +
						"Raspbery Pi is not include",
				productCategories.get(1), new BigDecimal("79.95"), 5,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/2/26/Pi2Go-Lite_Kit_-_Mainboard_%2814854757389%29.png/640px-Pi2Go-Lite_Kit_-_Mainboard_%2814854757389%29.png",
				""));
		products.add(new Product("Arduino Display",
				"Adafruit Arduino Display 160x80 pixel, monochrom",
				productCategories.get(1), new BigDecimal("17.25"), 5,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/a/a6/Adafruit_PiTFT_-_Front_%2814488806740%29.jpg/640px-Adafruit_PiTFT_-_Front_%2814488806740%29.jpg",
				""));

		return products;
	}
}
