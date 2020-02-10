-- bike_types: table
CREATE TABLE `bike_types` (
  `id`   int(11)                            NOT NULL AUTO_INCREMENT,
  `type` enum ('child', 'road', 'mountain') NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- bikes: table
CREATE TABLE `bikes` (
  `id`              int(11)               NOT NULL AUTO_INCREMENT,
  `brand`           varchar(45)           NOT NULL,
  `model`           varchar(45)           NOT NULL,
  `bike_type_id`    int(11)               NOT NULL,
  `rental_point_id` int(11)               NOT NULL,
  `status`          enum ('free', 'busy') NOT NULL DEFAULT 'free',
  PRIMARY KEY (`id`),
  KEY `bikes_bike-type__fk` (`bike_type_id`),
  KEY `bikes_rental-points__fk` (`rental_point_id`),
  CONSTRAINT `bikes_bike-type__fk` FOREIGN KEY (`bike_type_id`) REFERENCES `bike_types` (`id`),
  CONSTRAINT `bikes_rental-points__fk` FOREIGN KEY (`rental_point_id`) REFERENCES `rental_points` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- orders: table
CREATE TABLE `orders` (
  `id`         int(11)                     NOT NULL AUTO_INCREMENT,
  `start_date` datetime                    NOT NULL,
  `end_date`   datetime                             DEFAULT NULL,
  `user_id`    int(11)                     NOT NULL,
  `bike_id`    int(11)                     NOT NULL,
  `status`     enum ('active', 'finished') NOT NULL,
  `sum`        double                      NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Orders_id_uindex` (`id`),
  KEY `orders_users__fk` (`user_id`),
  KEY `orders_bikes__fk` (`bike_id`),
  CONSTRAINT `orders_bikes__fk` FOREIGN KEY (`bike_id`) REFERENCES `bikes` (`id`),
  CONSTRAINT `orders_users__fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- rental_cost: table
CREATE TABLE `rental_cost` (
  `id`           int(11) NOT NULL AUTO_INCREMENT,
  `bike_type_id` int(11) NOT NULL,
  `price`        double  NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `rental-cost_id_uindex` (`id`),
  KEY `rental-cost_bike-type__fk` (`bike_type_id`),
  CONSTRAINT `rental-cost_bike-type__fk` FOREIGN KEY (`bike_type_id`) REFERENCES `bike_types` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- rental_points: table
CREATE TABLE `rental_points` (
  `id`     int(11)     NOT NULL AUTO_INCREMENT,
  `name`   varchar(45) NOT NULL,
  `adress` varchar(72) NOT NULL,
  `tel`    varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `rental-points_id_uindex` (`id`),
  UNIQUE KEY `rental-points_name_uindex` (`name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- users: table
CREATE TABLE `users` (
  `id`       int(11)                         NOT NULL AUTO_INCREMENT,
  `name`     varchar(45)                     NOT NULL,
  `surname`  varchar(45)                     NOT NULL,
  `login`    varchar(45)                     NOT NULL,
  `password` varchar(45)                     NOT NULL,
  `role`     enum ('user', 'admin', 'guest') NOT NULL,
  `tel`      int(11)                         NOT NULL,
  `state`    enum ('active', 'blocked')      NOT NULL DEFAULT 'active',
  `balance`  double                                   DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idUsers_UNIQUE` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;


