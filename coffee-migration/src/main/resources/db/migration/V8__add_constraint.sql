ALTER TABLE coffee_order_item
  ADD CONSTRAINT COI_CO FOREIGN KEY (order_id)
    REFERENCES coffee_order (id);