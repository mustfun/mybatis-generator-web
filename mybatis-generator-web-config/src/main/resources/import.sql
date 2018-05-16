create table db_source
(
	id INTEGER
		primary key
		 autoincrement,
	db_name varchar(20),
	db_address varchar(40),
	user_name varchar(40),
	password varchar(40),
	create_time datetime
)
;


create table template
(
  id INTEGER not null
    primary key
  autoincrement,
  tep_name VARCHAR(40),
  tep_desc varchar(40),
  tep_content TEXT,
  create_by VARCHAR(40),
  type INTEGER,
  create_time timestamp
);