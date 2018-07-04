insert into time_slots
	values ('0', 0600, 0900, 'Breakfast');
insert into time_slots
	values ('1', 0600, 0900, 'Lunch');
insert into time_slots
	values ('2', 0600, 0900, 'Dinner');

insert into item_types
	values ('0', 'Drink');
insert into item_types
	values ('1', 'Side');
insert into item_types
	values ('2', 'Entree');
insert into item_types
	values ('3', 'Package');


insert into items
	values ('0', 'Rice', 'y', '1', 'Steamed Basmati Rice', '0', null, 1.00);
insert into items
	values ('1', 'Beans', 'y', '1', 'Can of Beans', '1', null, 1.20);
insert into items
	values ('2', 'Rice and Beans', 'y', '3', 'The Rice and the Beans', '0', null, 2.00);
insert into items
	values ('3', 'Water', 'y', '0', 'Molten Ice', '1', null, 0.50);
insert into items
	values ('4', 'Soda', 'y', '0', 'Bubbly Molten Ice', '1', null, 0.70);
insert into items
	values ('5', 'Chicken', 'n', '2', 'Pan Friend Chicken', '2', null, 6.99);

insert into specials
	values ('0', 10);
insert into specials
	values ('4', 35);

insert into delivery_statuses
	values ('0', 'pending');
insert into delivery_statuses
	values ('1', 'on delivery');
insert into delivery_statuses
	values ('2', 'delivered');
insert into delivery_statuses
	values ('3', 'cancelled');


insert into delivery_methods
	values ('0', 'pickup');
insert into delivery_methods
	values ('1', 'delivery');
insert into delivery_methods
	values ('2', 'dine-in');


insert into user_statuses
	values ('0', 'Unverified');
insert into user_statuses
	values ('1', 'Verified');
insert into user_statuses
	values ('2', 'BANNED');
insert into user_statuses
	values ('3', 'Admin');
insert into user_statuses
	values ('4', 'Employee');
insert into user_statuses
	values ('5', 'Manager');


insert into users
	values ('0','Eric','Karnis','6035559577','eric@karnis.com','hey','0');
insert into users
	values ('1','Mindy','Manager','5069993444','man@mail.com','pass','5');
insert into users
	values ('2','Alex','Admin','8049772556','alex@mail.com','pass','3');
insert into users
	values ('3','Bobby','Banned','6666665566','bob@mail.com','pass','2');


insert into locations
	values ('0','0',50.5,'100 example st', 'townville', 'WA', 'USA','01440');
insert into locations
	values ('1','1',56.5,'700 example st', 'wayland', 'MA', 'USA','34567');
insert into locations
	values ('2','2',20.4,'600 example st', 'billsville', 'FL', 'USA','08990');
insert into locations
	values ('3','3',20.4,'600 example st', 'bannedville', 'FL', 'USA','08990');

insert into cards
	values ('0','0',5,(TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss')) ,123);
insert into cards
	values ('1','0',5435,(TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss')) ,123);
insert into cards
	values ('2','1',523453,(TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss')) ,123);
insert into cards
	values ('3','2',32545,(TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss')) ,123);
insert into cards
	values ('4','3',642645,(TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss')) ,123);

insert into stores
	values ('0','0','Deer Valley',1,0,0800,2100);

insert into orders 
	values ('0','0',2.50,15.50,0,0,'0','hey','0','0','0');
insert into orders 
	values ('1','1',5.00,16.50,0,0,'0','run to house','1','0','2');

insert into order_items
	values ('0','0');
insert into order_items
	values ('1','2');
insert into order_items
	values ('1','3');
insert into order_items
	values ('1','4');
