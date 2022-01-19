set foreign_key_checks = 0;

delete from tb_drugstore;
delete from drugstore_phones;

set foreign_key_checks = 1;

alter table tb_drugstore auto_increment = 1;

insert into tb_drugstore (name, cnpj, email, address_postal_code, address_street, address_number, address_neighborhood, address_city, address_state, created_at, updated_at) values ('Drogaria Araujo', '98224585000128', 'araujo@email.com', '30656231', 'Rua da Bahia', '656', 'Centro', 'Salvador', 'BA', utc_timestamp, utc_timestamp);
insert into tb_drugstore (name, cnpj, email, address_postal_code, address_street, address_number, address_neighborhood, address_city, address_state, created_at, updated_at) values ('Droga Raia', '69828121000122', 'drogaraia@email.com', '35456546', 'Rua da Santos', '6654', 'Centro', 'Belo Horizonte', 'MG', utc_timestamp, utc_timestamp);

insert into drugstore_phones (drugstore_id, phone) values (1, '(37) 98654-8779');
insert into drugstore_phones (drugstore_id, phone) values (1, '(51) 96545-3214');

insert into drugstore_phones (drugstore_id, phone) values (2, '(31) 6542-3213');
insert into drugstore_phones (drugstore_id, phone) values (2, '(32) 96546-3215');
