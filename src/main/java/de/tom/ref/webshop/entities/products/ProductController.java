package de.tom.ref.webshop.entities.products;

import de.tom.ref.webshop.errorhandling.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {
    private final ProductRepository repository;
    private final ProductService service;

    @Autowired
    public ProductController(ProductRepository repository,
            ProductService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("")
    public List<Product> getAll(@RequestParam(value = "category_id", defaultValue = "0") Integer categoryId) {
        return service.getProducts(categoryId);
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping("/add")
    public void addProduct(@RequestParam(value = "category_id", defaultValue = "0") Integer categoryId,
                           @RequestBody Product product) {
        service.addProduct(categoryId, product);
    }

    @PostMapping("/del/{id}")
    public void delById(@PathVariable Integer id) {
        service.delProduct(id);
    }

}
