package de.tom.ref.webshop.controllers;

import de.tom.ref.webshop.entities.carts.Cart;
import de.tom.ref.webshop.entities.carts.CartContent;
import de.tom.ref.webshop.entities.carts.CartContentService;
import de.tom.ref.webshop.entities.carts.CartService;
import de.tom.ref.webshop.entities.customers.Customer;
import de.tom.ref.webshop.entities.customers.CustomerService;
import de.tom.ref.webshop.entities.order.Order;
import de.tom.ref.webshop.entities.order.OrderService;
import de.tom.ref.webshop.entities.products.Product;
import de.tom.ref.webshop.entities.products.ProductCategory;
import de.tom.ref.webshop.entities.products.ProductCategoryService;
import de.tom.ref.webshop.entities.products.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
public class WebshopController {
    private final CustomerService customerService;
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final CartService cartService;
    private final CartContentService cartContentService;
    private final OrderService orderService;

    @GetMapping("/login")
    public String signin() {
        return "signin_form";
    }

    @RequestMapping("/")
    public ModelAndView index(ModelAndView model) {
        model.setViewName("index");
        return model;
    }

    @GetMapping("/info")
    public ModelAndView getInfo(ModelAndView model) {
        Customer customer = customerService.getSignInCustomer();
        model.addObject("user", customer);
        model.setViewName("info");
        return model;
    }

    @RequestMapping("/shop")
    public ModelAndView index(@RequestParam(value = "id", defaultValue = "0") Integer productCategoryId,
                              ModelAndView model) {
        Customer customer = customerService.getSignInCustomer();
        Cart cart = cartService.getCartOfCustomer(customer);

        List<ProductCategory> productCategories = productCategoryService.getAll();
        List<Product> products = productService.getProducts(productCategoryId);
        model.addObject("user", customer);
        model.addObject("products", products);
        model.addObject("productCategoryId", productCategoryId);
        model.addObject("productCategories", productCategories);
        model.addObject("cartContentSize", cartContentService.getAmountOfProductsInCart(cart));
        model.setViewName("shop");
        return model;
    }

    @RequestMapping("/filter")
    public ModelAndView filter(@RequestParam(value = "id", defaultValue = "0") Integer productCategoryId,
                               ModelAndView model) {
        model.setViewName("redirect:shop?id=" + String.valueOf(productCategoryId));
        return model;
    }

    @PostMapping("/add_product_to_cart")
    public ModelAndView addProductToCart(@RequestParam(value = "productId") Integer productId,
                                         ModelAndView model) {
        Customer customer = customerService.getSignInCustomer();
        Cart cart = cartService.getCartOfCustomer(customer);
        Product product = productService.getProduct(productId);
        cartContentService.addProductToCart(cart, product);

        List<ProductCategory> productCategories = productCategoryService.getAll();
        List<Product> products = productService.getProducts(0);

        model.addObject("user", customer);
        model.addObject("products", products);
        model.addObject("productCategories", productCategories);
        model.addObject("cartContentSize", cartContentService.getAmountOfProductsInCart(cart));
        model.setViewName("redirect:shop");
        return model;
    }

    @PostMapping("/delete_product_from_cart")
    public ModelAndView deleteProductFromCart(@RequestParam(value = "cartContentId") Integer cartContentId,
                                              ModelAndView model) {
        Customer customer = customerService.getSignInCustomer();
        Cart cart = cartService.getCartOfCustomer(customer);
        cartService.deleteProductFromCart(cart, cartContentId);

        List<CartContent> cartContents = cartService.getCartContents(cart);

        model.addObject("user", customer);
        model.addObject("cart", cart);
        model.addObject("cartContents",  cartContents);
        model.addObject("subTotalSum", cartContentService.calculateSubtotalSum(cart));;
        model.addObject("cartContentSize", cartContentService.getAmountOfProductsInCart(cart));
        model.setViewName("redirect:cart");
        return model;
    }

    @GetMapping("/cart")
    public ModelAndView getCart(ModelAndView model) {
        Customer customer = customerService.getSignInCustomer();
        Cart cart = cartService.getCartOfCustomer(customer);
        List<CartContent> cartContents = cartService.getCartContents(cart);

        model.addObject("user", customer);
        model.addObject("cart", cart);
        model.addObject("cartContents",  cartContents);
        model.addObject("subTotalSum", cartContentService.calculateSubtotalSum(cart));;
        model.addObject("cartContentSize", cartContentService.getAmountOfProductsInCart(cart));
        model.setViewName("cart");
        return model;
    }

    @GetMapping(value = "/cartContent")
    public ModelAndView changeQuantity(
            @RequestParam(value = "id") Integer cartContentId,
            @RequestParam(value = "change") Integer changeValue,
            ModelAndView model) {
        log.info("Change cart quantity id={} change={}", cartContentId, changeValue);
        Customer customer = customerService.getSignInCustomer();
        Cart cart = cartService.getCartOfCustomer(customer);

        // change quantity
        cartService.changeQuantityOfCartContent(cart, cartContentId, changeValue);

        List<CartContent> cartContents = cartService.getCartContents(cart);
        model.addObject("user", customer);
        model.addObject("cart", cart);
        model.addObject("cartContents", cartContents);
        model.addObject("subTotalSum", cartContentService.calculateSubtotalSum(cart));;
        model.addObject("cartContentSize", cartContentService.getAmountOfProductsInCart(cart));
        model.setViewName("redirect:cart");
        return model;
    }

    @GetMapping("/order")
    public ModelAndView getOrder(ModelAndView model) {
        Customer customer = customerService.getSignInCustomer();
        Cart cart = cartService.getCartOfCustomer(customer);
        List<CartContent> cartContents = cartService.getCartContents(cart);

        model.addObject("user", customer);
        model.addObject("cart", cart);
        model.addObject("cartContents",  cartContents);
        model.addObject("cartContentSize", cartContentService.getAmountOfProductsInCart(cart));
        model.addObject("subTotalSum", cartContentService.calculateSubtotalSum(cart));
        model.addObject("shippingCosts", cartContentService.calculateShippingCosts(cart));
        model.addObject("totalSum", cartContentService.calculateTotalSum(cart));
        model.setViewName("order");
        return model;
    }

    @GetMapping("/order_confirm")
    public ModelAndView confirmaOrder(ModelAndView model) {
        Customer customer = customerService.getSignInCustomer();
        Cart cart = cartService.getCartOfCustomer(customer);
        Order order = orderService.createOrderFromCart(customer, cart);

        model.addObject("user", customer);
        model.addObject("cart", cart);
        model.addObject("order", order);
        model.setViewName("order_confirm");
        return model;
    }

    @GetMapping("/order_cancel")
    public ModelAndView cancelOrder(ModelAndView model) {
        Customer customer = customerService.getSignInCustomer();
        Cart cart = cartService.getCartOfCustomer(customer);
        cart = cartService.emptyCart(cart);

        List<ProductCategory> productCategories = productCategoryService.getAll();
        List<Product> products = productService.getProducts(0);
        model.addObject("user", customer);
        model.addObject("products", products);
        model.addObject("productCategoryId", 0);
        model.addObject("productCategories", productCategories);
        model.addObject("cartContentSize", cartContentService.getAmountOfProductsInCart(cart));
        model.setViewName("redirect:shop");
        return model;
    }


}
