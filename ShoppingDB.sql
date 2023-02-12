-- CREATE DATABASE IF NOT EXISTS shoppingDB;
USE shoppingDB;

-- DROP TABLE IF EXISTS User;
-- CREATE TABLE IF NOT EXISTS User (
-- 	userId int auto_increment PRIMARY KEY,
--     email varchar(42),
--     username varchar(42),
--     password varchar(42),
--     isSeller boolean
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
--     excutionRetailPrice float,
--     excutionWholesalePrice float,
--     FOREIGN KEY (orderId) REFERENCES Orders(orderId),
--     FOREIGN KEY (productId) REFERENCES Product(productId)
-- );


SELECT * FROM user;
SELECT * FROM product;
SELECT * FROM productWatchList;
SELECT * FROM orders;
SELECT * FROM orderProduct;