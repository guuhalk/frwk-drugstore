DELETE FROM tb_user;

TRUNCATE TABLE tb_user RESTART IDENTITY;

insert into tb_user (city, neighborhood, number, postal_code, state, street, birthday, cpf, created_at, email, name, password, updated_at, user_type) values ('Belo Horizonte', 'Centro', '50', '30350280', 'MG', 'Rua 20', '1990-10-30', '11779016689', current_timestamp, 'danieldutra@email.com', 'Daniel', '$2a$10$LET7kfo8wLySvOYKamv5yODm8yjsSEcogfnuOj//RfRsUF9wl05iu', current_timestamp, 'ADMIN');
insert into tb_user (city, neighborhood, number, postal_code, state, street, birthday, cpf, created_at, email, name, password, updated_at, user_type) values ('São Paulo', 'Sé', '10', '205465540', 'Sp', 'Rua 1050', '1994-06-25', '12838562045', current_timestamp, 'airton@email.com', 'Airton', '$2a$10$LET7kfo8wLySvOYKamv5yODm8yjsSEcogfnuOj//RfRsUF9wl05iu', current_timestamp, 'CLIENT');
