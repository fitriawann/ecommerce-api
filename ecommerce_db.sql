drop database ecommercedb;
drop user ecommerce;
create user ecommerce with password 'password';
create database ecommercedb with template=template0 owner=ecommerce;
\connect ecommercedb;
alter default privileges grant all on tables to ecommerce;
alter default privileges grant all on sequences to ecommerce;

create type gen as enum ('m', 'f');
create table ec_users(
user_id integer primary key not null,
first_name varchar(20) not null,
last_name varchar(20) not null,
gender gen not null,
birthday date not null,
phone varchar(20) not null,
email varchar(30) not null,
password text not null
);

create table ec_stores(
store_id integer primary key not null,
title varchar(20) not null,
description varchar(50) not null
);

create table ec_brands(
brand_id integer primary key not null,
title varchar(20) not null,
description varchar(50) not null
);

create table ec_categories(
category_id integer primary key not null,
title varchar(20) not null,
description varchar(50) not null
);

create table ec_products(
product_id integer primary key not null,
store_id integer not null,
brand_id integer not null,
title varchar(20) not null,
code varchar(20) not null,
price numeric(10,2) not null,
description varchar(50) not null
);

alter table ec_products add constraint prod_store_fk
foreign key (store_id) references ec_stores(store_id);
alter table ec_products add constraint prod_brand_fk
foreign key (brand_id) references ec_brands(brand_id);

create table ec_products_categories(
product_id integer not null,
category_id integer not null
);

alter table ec_products_categories add constraint prod_cat_prod_fk
foreign key (product_id) references ec_products(product_id);
alter table ec_products_categories add constraint prod_cat_cat_fk
foreign key (category_id) references ec_categories(category_id);

create sequence ec_users_seq increment 1 start 1;
create sequence ec_stores_seq increment 1 start 1;
create sequence ec_brands_seq increment 1 start 1;
create sequence ec_categories_seq increment 1 start 1;
create sequence ec_products_seq increment 1 start 1;

insert into ec_stores(store_id, title, description) VALUES(NEXTVAL('ec_stores_seq'), 'toko', 'this is toko store');
insert into ec_brands(brand_id, title, description) VALUES(NEXTVAL('ec_brands_seq'), 'merk', 'this is merk brand');
insert into ec_categories(category_id, title, description) VALUES(NEXTVAL('ec_categories_seq'), 'toys', 'this is toys category');
insert into ec_categories(category_id, title, description) VALUES(NEXTVAL('ec_categories_seq'), 'hoby', 'this is hoby category');
insert into ec_products(product_id, store_id, brand_id, title, code, price, description) VALUES(NEXTVAL('ec_products_seq'), 1, 1, 'gundam', 'GT001', 700000, 'this is gundam product');
insert into ec_products_categories(product_id, category_id) VALUES (1, 1);
insert into ec_products_categories(product_id, category_id) VALUES (1, 2);