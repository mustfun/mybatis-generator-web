drop table if exists city;

create table city (id int primary key auto_increment, name varchar(30), state varchar(20), country varchar(20));

insert into city (name, state, country) values ('San Francisco', 'CA', 'US');