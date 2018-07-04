create table time_slots(
	time_slot_id varchar(4000) primary key,
	slot_start int,
	slot_end int,
	slot_name varchar(4000)
);

create table item_types(
	item_type_id varchar(4000) primary key,
	item_type varchar(4000)
);


create table items(
	item_id varchar(4000) primary key,
	name varchar(4000),
	vegetarian char(1),
	item_type_id varchar(4000) references item_types(item_type_id),
	description varchar(4000),
	time_slot_id varchar(4000) references time_slots(time_slot_id),
	photo varchar(4000),
	price number(5,2),
	constraint check_vegetarian
	check(vegetarian in ('y', 'n'))
);
create table specials(
	item_id varchar(4000) references items(item_id) ON DELETE CASCADE,
	discount_percentage int
);

create table delivery_statuses(
	delivery_status_id varchar(4000) primary key,
	delivery_status varchar(4000)
);
create table delivery_methods(
	delivery_method_id varchar(4000) primary key,
	delivery_method varchar(4000)
);
create table user_statuses(
	user_status_id varchar(4000) primary key,
	user_status varchar(4000)
);
create table users(
	user_id varchar(4000) primary key,
	first varchar(4000),
	last varchar(4000),
	phone varchar(4000),
	email varchar(4000) not null unique,
	password varchar(4000) not null,
	user_status_id varchar(4000) references user_statuses(user_status_id)
);
create table locations(
	location_id varchar(4000) primary key,
	user_id varchar(4000) references users(user_id),
	tax_rate number(5,2),
	street varchar(4000),
	city varchar(4000),
	state varchar(4000),
	country varchar(4000),
	zip varchar(4000),
	constraint limit_tax
	check(tax_rate between 0 and 100.00)
);
create table cards(
	card_id varchar(4000) primary key,
	user_id varchar(4000) references users(user_id) ON DELETE CASCADE,
	card_number int,
	expiry_date date,
	security_code int
);
create table stores(
	store_id varchar(4000) primary key,
	location_id varchar(4000) references locations(location_id),
	store varchar(4000),
	phone_number int,
	manager_id varchar(4000) references users(user_id),
	open_time int,
	close_time int
);
create table orders(
	order_id varchar(4000) primary key,
	user_id varchar(4000) references users(user_id),
	tip number(5,2),
	total_price number(7,2),
	placed_timestamp int,
	delivery_timestamp int,
	card_id varchar(4000) references cards(card_id),
	instructions varchar(4000),
	delivery_method_id varchar(4000) references delivery_methods(delivery_method_id),
	store_id varchar(4000) references stores(store_id),
	delivery_status_id varchar(4000) references delivery_statuses(delivery_status_id)
);
create table order_items(
	order_id varchar(4000) references orders(order_id),
	item_id varchar(4000) references items(item_id) 
);