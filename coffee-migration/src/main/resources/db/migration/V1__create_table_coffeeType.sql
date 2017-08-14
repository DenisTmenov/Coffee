CREATE TABLE coffee_type (
  id int NOT null AUTO_INCREMENT, 
  type_name VARCHAR(200) NOT null,
  price double NOT null, 
  disabled CHAR(1), 
  PRIMARY KEY (id)
) ;