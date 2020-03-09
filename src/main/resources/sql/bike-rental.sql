

CREATE DATABASE `bike-rental` DEFAULT CHAR SET utf8;

CREATE TABLE IF NOT EXISTS bike_types (
  'id' bigint NOT NULL PRIMARY KEY ,
  'type' enum ('child','road','mountain') NOT NULL );


  CREATE TABLE IF NOT EXISTS bikes (
    'id' bigint AUTO_INCREMENT NOT NULL PRIMARY KEY ,
    'brand' varchar(45) NOT NULL ,
    'model' varchar(45) NOT NULL ,
    'bike_type_id' bigint,
    'rental_point_id' bigint,
    'status' enum ('free', 'busy') DEFAULT 'free' NOT NULL ,
    CONSTRAINT bikes_bike_types__fk FOREIGN KEY ('bike_type_id')REFERENCES bike_types('id'),
    CONSTRAINT bikes_rental_points__fk FOREIGN KEY ('rental_point_id') REFERENCES rental_points('id')
    );
CREATE INDEX bikes_bike_types__fk
  ON bikes (bike_type_id);

CREATE INDEX bikes_rental_points__fk
  ON bikes (rental_point_id);

CREATE TABLE IF NOT EXISTS orders(
  'id' bigint NOT NULL PRIMARY KEY AUTO_INCREMENT UNIQUE,
  'start_date' datetime NOT NULL ,
  'end_date' datetime  DEFAULT NULL ,
  'user_id' bigint NOT NULL ,
  'bike_id' bigint NOT NULL ,
  'status' enum('active','finished') NOT NULL ,
  'sum' double NOT NULL,
  CONSTRAINT orders_users__fk FOREIGN KEY ('user_id') REFERENCES users ('id'),
  CONSTRAINT  orders_bikes__fk FOREIGN KEY ('bike_id') REFERENCES bikes('id')
);
CREATE INDEX orders_bikes__fk
  ON orders (bike_id);

CREATE INDEX orders_users__fk
  ON orders (user_id);

CREATE TABLE IF NOT EXISTS rental_cost(
  "id" bigint not null AUTO_INCREMENT UNIQUE PRIMARY KEY ,
  'bike_type_id' bigint NOT NULL,
  'price' double NOT NULL ,
  CONSTRAINT rental_cost_bike_types__fk FOREIGN KEY ('bike_type_id') REFERENCES bike_types('id')
);

CREATE TABLE IF NOT EXISTS rental_points(
  'id' bigint NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
  "name" varchar(45) NOT NULL UNIQUE,
  'adress' varchar(45) NOT NULL ,
  'tel' varchar(45) NOT NULL
  );

CREATE TABLE IF NOT EXISTS users(
  'id' bigint NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY ,
  'name' varchar(45) NOT NULL ,
  'surname' varchar(45) NOT NULL ,
  'login' varchar(45) NOT NULL,
  'password' varchar(45) NOT NULL ,
  'role' enum('user','admin')DEFAULT 'user' NOT NULL,
  'tel' int NOT NULL ,
  'state' enum('active','blocked') NOT NULL DEFAULT ('active'),
  balance double NOT NULL
);


