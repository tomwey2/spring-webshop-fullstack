DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
  order_id          INT         NOT NULL AUTO_INCREMENT,
  customer_id       INT         DEFAULT NULL,
  order_number      VARCHAR(20) NOT NULL,
  order_date        DATETIME    NOT NULL,
  create_date       DATETIME    NOT NULL,
  last_update       TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (order_id),
);

DROP TABLE IF EXISTS order_details;
CREATE TABLE order_details (
  order_id          INT         NOT NULL,
  product_id        INT         NOT NULL,
  quantity          INT         DEFAULT 0,
);
