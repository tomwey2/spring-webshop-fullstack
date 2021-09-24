package de.tom.ref.webshop.controllers;

import de.tom.ref.webshop.entities.carts.Cart;
import de.tom.ref.webshop.entities.carts.CartContent;
import de.tom.ref.webshop.entities.carts.CartService;
import de.tom.ref.webshop.entities.customers.Customer;
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

import java.math.BigDecimal;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
public class WebshopController {
    private final RegistrationService registrationService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final CartService cartService;

    @RequestMapping("/")
    public String index(Model model) {
        Customer customer = customerService.getSignInCustomer();
        List<ProductCategory> productCategories = productCategoryService.getAll();
        List<Product> products = productService.getProducts(0);
        model.addAttribute("user", customer);
        model.addAttribute("products", products);
        model.addAttribute("productCategories", productCategories);
        model.addAttribute("cartContentSize", cartService.getAmountOfProductsInCart(customer));
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

    @PostMapping("/product_to_cart")
    public String addProductToCart(@RequestParam(value = "productId") Integer productId, Model model) {
        Customer customer = customerService.getSignInCustomer();
        Cart cart = cartService.getCartOfCustomer(customer);
        Product product = productService.getProduct(productId);
        cartService.addProductToCart(cart, product);

        List<ProductCategory> productCategories = productCategoryService.getAll();
        List<Product> products = productService.getProducts(0);

        model.addAttribute("user", customer);
        model.addAttribute("products", products);
        model.addAttribute("productCategories", productCategories);
        model.addAttribute("cartContentSize", cartService.getAmountOfProductsInCart(customer));
        return "index";
    }

    @GetMapping("/cart")
    public String getCart(Model model) {
        Customer customer = customerService.getSignInCustomer();
        Cart cart = cartService.getCartOfCustomer(customer);
        List<CartContent> cartContents = cartService.getCartContents(cart);

        model.addAttribute("user", customer);
        model.addAttribute("cart", cart);
        model.addAttribute("cartContents",  cartContents);
        model.addAttribute("totalSum", cartService.calculateSubtotalPrice(cart));;
        model.addAttribute("cartContentSize", cartService.getAmountOfProductsInCart(customer));
        return "cart";
    }

    @GetMapping(value = "/cartContent")
    public String changeQuantity(
            @RequestParam(value = "id") Integer cartContentId,
            @RequestParam(value = "change") Integer changeValue,
            Model model) {
        log.info("Change cart quantity id={} change={}", cartContentId, changeValue);
        Customer customer = customerService.getSignInCustomer();
        Cart cart = cartService.getCartOfCustomer(customer);

        // change quantity
        CartContent cartContent = cartService.getCartContentById(cart, cartContentId);
        if ((cartContent.getQuantity() > 1 || changeValue > 0)
        && (cartContent.getQuantity()<cartContent.getProduct().getUnitsInStock() || changeValue < 0)) {
            cartContent.setQuantity(cartContent.getQuantity() + changeValue);
            cartContent.setPrice(cartContent.getProduct().getUnitPrice().multiply(new BigDecimal(cartContent.getQuantity())));
            cartContent = cartService.saveCartContent(cartContent);
        }

        List<CartContent> cartContents = cartService.getCartContents(cart);
        model.addAttribute("user", customer);
        model.addAttribute("cart", cart);
        model.addAttribute("cartContents", cartContents);
        model.addAttribute("totalSum", cartService.calculateSubtotalPrice(cart));;
        model.addAttribute("cartContentSize", cartService.getAmountOfProductsInCart(customer));
        return "redirect:/cart";
    }

}
