CREATE TABLE coffee_order (
  id int NOT null, 
  order_date datetime NOT null, 
  name VARCHAR(100), 
  delivery_address VARCHAR(200) NOT null, 
  cost double, 
  PRIMARY KEY (id)
) ;