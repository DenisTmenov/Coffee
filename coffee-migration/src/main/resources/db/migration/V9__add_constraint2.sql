ALTER TABLE coffee_order_item
  ADD CONSTRAINT COI_CT FOREIGN KEY (type_id)
    REFERENCES coffee_type (id);