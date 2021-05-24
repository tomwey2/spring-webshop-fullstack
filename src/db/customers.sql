DROP TABLE IF EXISTS customers;
CREATE TABLE customers (
  customer_id       INT NOT NULL AUTO_INCREMENT,
  first_name        VARCHAR(50) NOT NULL,
  last_name         VARCHAR(50) NOT NULL,
  email             VARCHAR(50) DEFAULT NULL,
  address_line1     VARCHAR(50) NOT NULL,
  address_line2     VARCHAR(50) DEFAULT NULL,
  city              VARCHAR(50) NOT NULL,
  country           VARCHAR(50) NOT NULL,
  active            BOOLEAN     NOT NULL DEFAULT TRUE,
  create_date       DATETIME    NOT NULL,
  last_update       TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (customer_id),
  KEY idx_last_name (last_name)
);
