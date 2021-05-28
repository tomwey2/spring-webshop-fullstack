DROP TABLE IF EXISTS customers;
CREATE TABLE customers (
  customer_id       INT         NOT NULL AUTO_INCREMENT,
  first_name        VARCHAR(50) NOT NULL,
  last_name         VARCHAR(50) NOT NULL,
  email             VARCHAR(50) NOT NULL,
  password          VARCHAR(50) NOT NULL,
  address_line1     VARCHAR(50) NOT NULL,
  address_line2     VARCHAR(50) DEFAULT NULL,
  city              VARCHAR(50) NOT NULL,
  postal_code       VARCHAR(10) NOT NULL,
  country           VARCHAR(50) NOT NULL,
  active            BOOLEAN     NOT NULL DEFAULT TRUE,
  create_date       DATETIME    NOT NULL,
  last_update       TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (customer_id),
  KEY idx_email (email),
  KEY idx_last_name (last_name)
);

INSERT INTO customers (first_name, last_name, email, password, address_line1, address_line2,
    city, postal_code, country, active, create_date)
VALUES
('Alfred', 'Mustermann', 'muster@test.de', 'password','Alpenstraße 1', '', 'München', '80000', 'Germany', true, CURTIME());
