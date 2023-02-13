-- CREATE DATABASE IF NOT EXISTS shoppingDB;
USE shoppingDB;

-- DROP TABLE IF EXISTS User;
-- CREATE TABLE IF NOT EXISTS User (
-- 	userId int auto_increment PRIMARY KEY,
--     email varchar(42),
--     username varchar(42),
--     password varchar(42),
--     seller boolean
-- );


-- DROP TABLE IF EXISTS Product;
-- CREATE TABLE IF NOT EXISTS Product (
-- 	productId int auto_increment PRIMARY KEY,
--     name varchar(42),
--     description varchar(400),
--     retailPrice float,
--     wholesalePrice float,
--     stockQuantity int
-- );

-- INSERT INTO Product(name, description, retailPrice, wholesalePrice, stockQuantity) VALUES ("Shoes1", "This is shoes 1", 100, 70, 90);
-- INSERT INTO Product(name, description, retailPrice, wholesalePrice, stockQuantity) VALUES ("Pants1", "This is pants 1", 82, 40, 120);
-- INSERT INTO Product(name, description, retailPrice, wholesalePrice, stockQuantity) VALUES ("Dress1", "This is dress 1", 180, 120, 20);


-- DROP TABLE IF EXISTS ProductWatchList;
-- CREATE TABLE IF NOT EXISTS ProductWatchList (
-- 	userId int,
--     productId int,
--     FOREIGN KEY (userId) REFERENCES User(userId),
--     FOREIGN KEY (productId) REFERENCES Product(productId)
-- );


-- DROP TABLE IF EXISTS Orders;
-- CREATE TABLE IF NOT EXISTS Orders (
-- 	orderId int auto_increment PRIMARY KEY,
--     userId int,
--     orderStatus varchar(42),
--    datePlaced timestamp,
--    FOREIGN KEY (userId) REFERENCES User(userId)
-- );


-- DROP TABLE IF EXISTS OrderProduct;
-- CREATE TABLE IF NOT EXISTS OrderProduct (
-- 	orderProductId int auto_increment PRIMARY KEY,
--     orderId int,
--     productId int,
--     purchasedQuantity int,
--     executionRetailPrice float,
--     executionWholesalePrice float,
--     FOREIGN KEY (orderId) REFERENCES Orders(orderId),
--     FOREIGN KEY (productId) REFERENCES Product(productId)
-- );



SELECT * FROM user;
SELECT * FROM product;
SELECT * FROM productWatchList;
SELECT * FROM orders;
SELECT * FROM orderProduct;