DROP TABLE IF EXISTS customers;
CREATE TABLE customers (
  customer_id       INT         NOT NULL AUTO_INCREMENT,
  user_name         VARCHAR(20) NOT NULL,
  first_name        VARCHAR(50) NOT NULL,
  last_name         VARCHAR(50) NOT NULL,
  email             VARCHAR(50) DEFAULT NULL,
  address_line1     VARCHAR(50) NOT NULL,
  address_line2     VARCHAR(50) DEFAULT NULL,
  city              VARCHAR(50) NOT NULL,
  postal_code       VARCHAR(10) NOT NULL,
  country           VARCHAR(50) NOT NULL,
  active            BOOLEAN     NOT NULL DEFAULT TRUE,
  create_date       DATETIME    NOT NULL,
  last_update       TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (customer_id),
  KEY idx_user_name (user_name),
  KEY idx_last_name (last_name)
);

INSERT INTO customers (user_name, first_name, last_name, email, address_line1, address_line2,
    city, postal_code, country, active, create_date)
VALUES
('user1', 'Alfred', 'Mustermann', 'mustermann@email.de', 'Alpenstraße 1', '', 'München', '80000', 'Germany', true, CURTIME());
