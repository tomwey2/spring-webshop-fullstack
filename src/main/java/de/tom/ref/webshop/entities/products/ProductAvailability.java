package de.tom.ref.webshop.entities.products;

/**
 * The availability attribute tells if a product is in stock.
 */
public enum ProductAvailability {
    /* The product is in stock and can be delivered. */
    in_stock,
    /* The product is not available. Orders for this product can not be accepted. */
    out_of_stock,
    /* Customer can take orders for this product but it's not been released for sale. */
    preorder,
    /* The product is not available at the moment, but orders are accepted and it will
    * be shipped as soon as it becomes available again. */
    backorder
}
