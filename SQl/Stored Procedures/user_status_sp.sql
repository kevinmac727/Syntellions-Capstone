create or replace procedure sp_insert_user_status(user_status_id number, user_status varchar)
as 
begin
  insert into user_statuses
  values(user_status_id, user_status);
  
end;

create or replace procedure sp_update_user_status(user_status_num number, user_status_name varchar)
as 
begin
  update user_statuses
  set user_status=user_status_name
  where user_status_id=user_status_num;
end;

create or replace procedure sp_delete_user_status_by_id(user_status_num number)
as 
begin
  Delete from user_statuses where user_status_id = user_status_num;
end;
