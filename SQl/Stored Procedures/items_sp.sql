create or replace procedure updateItem(useID varchar, fname varchar, 
veg char, des varchar, timeSlot varchar,pic varchar, pric number)
as 
begin
  update items
  set name=fname, vegetarian=veg, description = des, time_slot_id = timeSlot, photo=pic, price=pric
  where item_id=useID;
end;

