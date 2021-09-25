package de.tom.ref.webshop.controllers;

import de.tom.ref.webshop.entities.carts.Cart;
import de.tom.ref.webshop.entities.carts.CartContent;
import de.tom.ref.webshop.entities.carts.CartContentService;
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
    private final CartContentService cartContentService;

    @RequestMapping("/")
    public String index(@RequestParam(value = "id", defaultValue = "0") Integer productCategoryId,
                        Model model) {
        Customer customer = customerService.getSignInCustomer();
        Cart cart = cartService.getCartOfCustomer(customer);

        List<ProductCategory> productCategories = productCategoryService.getAll();
        List<Product> products = productService.getProducts(productCategoryId);
        model.addAttribute("user", customer);
        model.addAttribute("products", products);
        model.addAttribute("productCategoryId", productCategoryId);
        model.addAttribute("productCategories", productCategories);
        model.addAttribute("cartContentSize", cartContentService.getAmountOfProductsInCart(cart));
        return "index";
    }

    @RequestMapping("/filter")
    public String filter(@RequestParam(value = "id", defaultValue = "0") Integer productCategoryId,
                        Model model) {
        Customer customer = customerService.getSignInCustomer();
        Cart cart = cartService.getCartOfCustomer(customer);
        List<ProductCategory> productCategories = productCategoryService.getAll();
        List<Product> products = productService.getProducts(productCategoryId);
        model.addAttribute("user", customer);
        model.addAttribute("products", products);
        model.addAttribute("productCategoryId", productCategoryId);
        model.addAttribute("productCategories", productCategories);
        model.addAttribute("cartContentSize", cartContentService.getAmountOfProductsInCart(cart));
        //return "redirect:/?id=" + String.valueOf(productCategoryId);
        return "index";
    }

    @PostMapping("/add_product_to_cart")
    public String addProductToCart(@RequestParam(value = "productId") Integer productId, Model model) {
        Customer customer = customerService.getSignInCustomer();
        Cart cart = cartService.getCartOfCustomer(customer);
        Product product = productService.getProduct(productId);
        cartContentService.addProductToCart(cart, product);

        List<ProductCategory> productCategories = productCategoryService.getAll();
        List<Product> products = productService.getProducts(0);

        model.addAttribute("user", customer);
        model.addAttribute("products", products);
        model.addAttribute("productCategories", productCategories);
        model.addAttribute("cartContentSize", cartContentService.getAmountOfProductsInCart(cart));
        return "redirect:/";
    }

    @PostMapping("/delete_product_from_cart")
    public String deleteProductFromCart(@RequestParam(value = "cartContentId") Integer cartContentId,
                                        Model model) {
        Customer customer = customerService.getSignInCustomer();
        Cart cart = cartService.getCartOfCustomer(customer);
        cartService.deleteProductFromCart(cart, cartContentId);

        List<CartContent> cartContents = cartService.getCartContents(cart);

        model.addAttribute("user", customer);
        model.addAttribute("cart", cart);
        model.addAttribute("cartContents",  cartContents);
        model.addAttribute("subTotalSum", cartContentService.calculateSubtotalSum(cart));;
        model.addAttribute("cartContentSize", cartContentService.getAmountOfProductsInCart(cart));
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String getCart(Model model) {
        Customer customer = customerService.getSignInCustomer();
        Cart cart = cartService.getCartOfCustomer(customer);
        List<CartContent> cartContents = cartService.getCartContents(cart);

        model.addAttribute("user", customer);
        model.addAttribute("cart", cart);
        model.addAttribute("cartContents",  cartContents);
        model.addAttribute("subTotalSum", cartContentService.calculateSubtotalSum(cart));;
        model.addAttribute("cartContentSize", cartContentService.getAmountOfProductsInCart(cart));
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
        cartService.changeQuantityOfCartContent(cart, cartContentId, changeValue);

        List<CartContent> cartContents = cartService.getCartContents(cart);
        model.addAttribute("user", customer);
        model.addAttribute("cart", cart);
        model.addAttribute("cartContents", cartContents);
        model.addAttribute("subTotalSum", cartContentService.calculateSubtotalSum(cart));;
        model.addAttribute("cartContentSize", cartContentService.getAmountOfProductsInCart(cart));
        return "redirect:/cart";
    }

    @GetMapping("/order")
    public String getOrder(Model model) {
        Customer customer = customerService.getSignInCustomer();
        Cart cart = cartService.getCartOfCustomer(customer);
        List<CartContent> cartContents = cartService.getCartContents(cart);

        model.addAttribute("user", customer);
        model.addAttribute("cart", cart);
        model.addAttribute("cartContents",  cartContents);
        model.addAttribute("cartContentSize", cartContentService.getAmountOfProductsInCart(cart));
        model.addAttribute("subTotalSum", cartContentService.calculateSubtotalSum(cart));;
        model.addAttribute("shippingCosts", cartContentService.calculateShippingCosts(cart));;
        model.addAttribute("totalSum", cartContentService.calculateTotalSum(cart));;
        return "order";
    }

    @GetMapping("/info")
    public String getInfo(Model model) {
        Customer customer = customerService.getSignInCustomer();
        model.addAttribute("user", customer);
        return "info";
    }

    @GetMapping("/confirmation")
    public String confirmaOrder(Model model) {
        Customer customer = customerService.getSignInCustomer();
        model.addAttribute("user", customer);
        return "confirmation";
    }

    @GetMapping("/cancel")
    public String cancelOrder(Model model) {
        Customer customer = customerService.getSignInCustomer();
        Cart cart = cartService.getCartOfCustomer(customer);
        cart = cartService.emptyCart(cart);

        List<ProductCategory> productCategories = productCategoryService.getAll();
        List<Product> products = productService.getProducts(0);
        model.addAttribute("user", customer);
        model.addAttribute("products", products);
        model.addAttribute("productCategoryId", 0);
        model.addAttribute("productCategories", productCategories);
        model.addAttribute("cartContentSize", cartContentService.getAmountOfProductsInCart(cart));
        return "redirect:/";
    }


}
