package de.tom.ref.webshop.controllers;

import de.tom.ref.webshop.entities.customers.CustomerService;
import de.tom.ref.webshop.entities.products.Product;
import de.tom.ref.webshop.entities.products.ProductCategory;
import de.tom.ref.webshop.entities.products.ProductCategoryService;
import de.tom.ref.webshop.entities.products.ProductService;
import de.tom.ref.webshop.registration.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
public class WebshopController {
    private final RegistrationService registrationService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    @RequestMapping("/")
    public String index(Model model) {
        List<ProductCategory> productCategories = productCategoryService.getAll();
        List<Product> products = productService.getProducts(0);
        model.addAttribute("user", customerService.getSignInCustomer());
        model.addAttribute("products", products);
        model.addAttribute("productCategories", productCategories);
        return "index";
    }

    @PostMapping("/filter_products")
    public String filterProducts(@RequestParam(value = "id") Integer id, Model model) {
        log.info("Filter products with category={}", id);
        List<Product> products = productService.getProducts(id);
        List<ProductCategory> productCategories = productCategoryService.getAll();
        model.addAttribute("user", customerService.getSignInCustomer());
        model.addAttribute("products", products);
        model.addAttribute("productCategories", productCategories);
        return "index";
    }

    /*
    @GetMapping("/")
    public String getHomePage() {
        return "index";
    }

     */
}
