CREATE DATABASE IF NOT EXISTS webshop;
CREATE USER user IDENTIFIED BY 'user';
GRANT ALL PRIVILEGES ON webshop.* TO user;
show grants for user;