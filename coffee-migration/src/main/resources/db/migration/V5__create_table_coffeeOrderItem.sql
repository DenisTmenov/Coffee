CREATE TABLE coffee_order_item (
  id int NOT null AUTO_INCREMENT, 
  type_id int NOT null,
  order_id int NOT null, 
  quantity int, 
  PRIMARY KEY (id)
) ;