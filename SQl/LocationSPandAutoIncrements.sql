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

create or replace procedure SP_INSERT_LOCATION(userID varchar2, tax number, street varchar2,city varchar2, locationState varchar2, country varchar2, zip varchar2)
as
begin
  insert into locations
  values(seq_location.nextval, userID, tax, street, city, locationState, country, zip);
end SP_INSERT_LOCATION;

create or replace procedure sp_insert_user(user_id varchar, first varchar, last varchar, phone varchar, email varchar, password varchar, user_status_id varchar)
as 
begin
  insert into users
  values(seq_person.nextval, first, last, phone, email, password, user_status_id);
  
end;

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

