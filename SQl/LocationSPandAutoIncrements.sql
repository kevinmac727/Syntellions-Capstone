CREATE SEQUENCE seq_person
MINVALUE 1
START WITH 10
INCREMENT BY 1
CACHE 10; 

CREATE SEQUENCE seq_location
MINVALUE 1
START WITH 10
INCREMENT BY 1
CACHE 10; 

CREATE SEQUENCE seq_orders
MINVALUE 1
START WITH 10
INCREMENT BY 1
CACHE 10;

create or replace FUNCTION FN_INSERT_LOCATION(userID varchar2,tax number, street varchar2,city varchar2, locationState varchar2, country varchar2, zip varchar2) RETURN varchar2
as
begin
  insert into locations
  values(seq_location.nextval, userID, tax, street, city, locationState, country, zip);
  RETURN seq_location.currval;
end FN_INSERT_LOCATION;

SHOW ERRORS;
create or replace FUNCTION fn_insert_user(first varchar, last varchar, phone varchar, email varchar, password varchar, user_status_id varchar) RETURN varchar2
as 
begin
  insert into users
  values(seq_person.nextval, first, last, phone, email, password, user_status_id);
  RETURN seq_person.currval;
  
end fn_insert_user;

create or replace procedure sp_del_location_by_loc_id(locationID varchar2)
as
begin
  delete from locations
  where location_ID = locationID;
end sp_del_location_by_loc_id;  

create or replace procedure sp_update_location(locID varchar2, userID varchar2, tax number, lStreet varchar2, lCity varchar2, lState varchar2, lCountry varchar2, lZip varchar2)
as
begin
  update locations
  set user_id = userID, tax_rate = tax, street = lStreet, city = lCity, state = lState, country = lCountry, zip = lZip
  where location_id = locID;
end sp_update_location; 

create or replace function fn_insert_order(user_id varchar2, tip number, totalPrice number, timePlaced number, deliveryTime number, card_id varchar2, instructions varchar2,
                                          deliveryMethodID varchar2, storeID varchar2, deliveryStatusID varchar2) RETURN varchar2
as
begin
  INSERT INTO orders
  VALUES (seq_orders.nextval, user_id, tip, totalPrice, timePlaced, deliveryTime, card_id, instructions, deliveryMethodID, storeID, deliveryStatusID);
  RETURN seq_orders.currval;
end fn_insert_order;