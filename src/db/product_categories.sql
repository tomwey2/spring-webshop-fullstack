DROP TABLE IF EXISTS product_categories;
CREATE TABLE product_categories (
  category_id       INT         NOT NULL AUTO_INCREMENT,
  category_name     VARCHAR(50) NOT NULL,
  create_date       DATETIME    NOT NULL,
  last_update       TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (category_id),
  KEY idx_category_name (category_name)
);

INSERT INTO product_categories (category_name, create_date)
VALUES
('Raspberry Pi', CURTIME()),
('Arduino', CURTIME()),
('Cable & Cards', CURTIME()),
('Sensors', CURTIME());
