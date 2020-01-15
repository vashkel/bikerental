CREATE SCHEMA `bike-rental` DEFAULT CHARACTER SET utf8;

CREATE TABLE bike_types (
  'id' int NOT NULL PRIMARY KEY ,
  'type' ENUM ('child','road','mountain') NOT NULL );


  CREATE TABLE bikes (
    'id' int NOT NULL PRIMARY KEY ,
    'brand' varchar(45) NOT NULL ,
    'model' varchar(45) NOT NULL ,
    'bike_type_id' int,
    'rental_point_id' int,
    CONSTRAINT fk_bike_type FOREIGN KEY ('bike_type_id')REFERENCES bike_types('id'),
    CONSTRAINT fk_rental_point FOREIGN KEY ('rental_point_id') REFERENCES rental_points('id')
    );

CREATE TABLE orders(
  'id' int NOT NULL PRIMARY KEY AUTO_INCREMENT UNIQUE,
  'start_date' datetime NOT NULL ,
  'end_date' datetime ,
  'user_id' int,
  'bike_id' int ,
  'status' enum('active','finished') NOT NULL ,
  'sum' double,
  CONSTRAINT fk_user FOREIGN KEY ('user_id') REFERENCES users ('id'),
  CONSTRAINT  fk_bike FOREIGN KEY ('bike_id') REFERENCES bikes('id')
);

CREATE TABLE rental_cost(
  "id" int not null AUTO_INCREMENT UNIQUE PRIMARY KEY ,
  'bike_type_id' int NOT NULL,
  'price' double NOT NULL ,
  CONSTRAINT fk_bike_type FOREIGN KEY ('bike_type_id') REFERENCES bike_types('id')
);

CREATE TABLE rental_points(
  'id' int NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
  "name" varchar(45) NOT NULL UNIQUE,
  'adress' varchar(45) NOT NULL ,
  'tel' varchar(45) NOT NULL
  );

CREATE TABLE users(
  'id' int NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY ,
  'name' varchar(45) NOT NULL ,
  'surname' varchar(45) NOT NULL ,
  'login' varchar(45) NOT NULL,
  'password' varchar(45) NOT NULL ,
  'role' enum('user','admin','guest')NOT NULL,
  'tel' int NOT NULL ,
  'state' enum('active','blocked') NOT NULL default ('active'),
  balance double NOT NULL

)

