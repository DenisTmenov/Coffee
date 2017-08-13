CREATE TABLE coffee_order (
  id int NOT null AUTO_INCREMENT, 
  order_date VARCHAR(100) NOT null, 
  name VARCHAR(100), 
  delivery_address VARCHAR(200) NOT null, 
  cost double, 
  PRIMARY KEY (id)
) ;