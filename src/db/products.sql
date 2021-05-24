DROP TABLE IF EXISTS products;
CREATE TABLE products (
  product_id        INT         NOT NULL AUTO_INCREMENT,
  product_name      VARCHAR(50) NOT NULL,
  category_id       INT         DEFAULT NULL,
  unit_price        DECIMAL(10,2) DEFAULT 0,
  units_in_stock    INT         DEFAULT 0,
  units_on_order    INT         DEFAULT 0,
  active            BOOLEAN     NOT NULL DEFAULT TRUE,
  create_date       DATETIME    NOT NULL,
  last_update       TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (product_id),
  KEY idx_product_name (product_name)
);


INSERT INTO products (product_name, category_id, unit_price, units_in_stock, active, create_date)
VALUES
('Raspberry Pi 3 Model B+', 1, 38.50, 5, true, CURTIME()),
('Raspberry Pi  Netzteil', 1, 6.40, 12, true, CURTIME()),
('Arduino UNO', 2, 23.20, 4, true, CURTIME()),
('Arduino Nano', 2, 25.50, 6, true, CURTIME()),
('Arduino Sensor Kit', 2, 27.26, 2, true, CURTIME());
