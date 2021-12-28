CREATE SCHEMA IF NOT EXISTS userdb;

CREATE TABLE IF NOT EXISTS tb_user (
  id BIGSERIAL CONSTRAINT pk_id_user PRIMARY KEY,
  name varchar(80) NOT NULL,
  cpf varchar(15) UNIQUE NOT NULL,
  birthday date,
  email varchar(100) UNIQUE NOT NULL,
  password varchar(200) NOT NULL,
  user_type varchar(50) NOT NULL,
  postal_code varchar(10),
  street varchar(50),
  number varchar(6),
  neighborhood varchar(40),
  city varchar(50),
  state varchar(30),
  created_at timestamp not null,
  updated_at timestamp not null
);