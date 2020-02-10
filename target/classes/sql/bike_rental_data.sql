INSERT INTO bike_types(type) value ('road');
INSERT INTO bike_types(type) value ('mountain');
INSERT INTO bike_types(type) value ('child');

INSERT INTO rental_points(name, adress, tel) VALUES ('PointOne','Kuprevicha 33','0297085698');
INSERT INTO rental_points(name, adress, tel) VALUES ('PointTwo','Polevaya 25','0254569321');
INSERT INTO rental_points(name, adress, tel) VALUES ('PointThree','Esenina 55','0291255875');

INSERT INTO rental_cost(bike_type_id, price) VALUES('1','12.5');
INSERT INTO rental_cost(bike_type_id, price) VALUES('2','18.5');
INSERT INTO rental_cost(bike_type_id, price) VALUES('3','20.0');

# point 1
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES('Stinger','Defender','1','1','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES('Stinger','Defender','1','1','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES('Stinger','Defender','1','1','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES ('Stels','Navigator 750','2','1','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES ('Stels','Navigator 750','2','1','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES ('Stels','Navigator 750','2','1','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES('AIST','Turbo','3','1','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES('AIST','Turbo','3','1','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES('AIST','Turbo','3','1','free');
# point 2
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES('Stinger','Defender','1','2','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES('Stinger','Defender','1','2','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES('Stinger','Defender','1','2','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES ('Stels','Navigator 750','2','2','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES ('Stels','Navigator 750','2','2','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES ('Stels','Navigator 750','2','2','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES('AIST','Turbo','3','2','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES('AIST','Turbo','3','2','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES('AIST','Turbo','3','2','free');
# point 3
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES('Stinger','Defender','1','3','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES('Stinger','Defender','1','3','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES('Stinger','Defender','1','3','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES ('Stels','Navigator 750','2','3','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES ('Stels','Navigator 750','2','3','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES ('Stels','Navigator 750','2','3','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES('AIST','Turbo','3','3','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES('AIST','Turbo','3','3','free');
INSERT INTO bikes(brand, model, bike_type_id, rental_point_id,status) VALUES('AIST','Turbo','3','3','free');