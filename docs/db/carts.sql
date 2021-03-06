DROP TABLE IF EXISTS carts;
CREATE TABLE carts (
  cart_id           INT         NOT NULL AUTO_INCREMENT,
  customer_id       INT         DEFAULT NULL,
  create_date       DATETIME    NOT NULL,
  last_update       TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (cart_id)
);

DROP TABLE IF EXISTS cart_content;
CREATE TABLE cart_content (
  content_id        INT         NOT NULL AUTO_INCREMENT,
  cart_id           INT         NOT NULL,
  product_id        INT         NOT NULL,
  quantity          INT         DEFAULT 0,
  PRIMARY KEY (content_id)
);

INSERT INTO carts (customer_id, create_date)
VALUES
( 1, CURTIME());
