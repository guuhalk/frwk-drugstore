create table tb_drugstore (
	id bigint not null auto_increment,
	name varchar(80) not null,
	cnpj varchar(20) not null,
	email varchar(80),
	
	address_postal_code varchar(10),
	address_street varchar(80),
	address_number varchar(8),
	address_neighborhood varchar(50),
	address_city varchar(60),
	address_state varchar(50),
	
	created_at datetime not null,
	updated_at datetime not null,
	
	primary key (id)
) engine=InnoDB default charset=utf8;

create table drugstore_phones (
	drugstore_id bigint not null,
	phone varchar(17),
	UNIQUE INDEX (drugstore_id, phone),
	FOREIGN KEY (drugstore_id) REFERENCES tb_drugstore(id)

) engine=InnoDB default charset=utf8;
