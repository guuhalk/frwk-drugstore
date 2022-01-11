# frwk-drugstore

Para executar o projeto baixe o arquivo docker-compose.yml que está na pasta docker.

As pastas postgresdb_financials/scripts e postgresdb_users/scripts tambem devem ser copiadas e colocadas no mesmo local do arquivo docker-compose.

Esses scripts serão necessários pra criar os bancos de dados dos microserviços de usuários e financeiro.

Caso o microserviço de usuários e financeiro não estejam rodando será necessário criar os bancos de dados manualmente.

## Criar o banco de dados do microserviço de usuários

Abra um terminal e execute os comandos abaixo  
- **docker exec -it postgres_users bash**  
- **psql -U root**  
- **CREATE USER postgres WITH PASSWORD 'admin';**  
- **CREATE DATABASE userdb;**  
- **GRANT ALL PRIVILEGES ON DATABASE userdb TO postgres;**  
- **\q**  
- **exit**  

## Criar o banco de dados do microserviço financeiro

Abra um terminal e execute os comandos abaixo  
- **docker exec -it postgres_financials bash**  
- **psql -U root**  
- **CREATE USER postgres WITH PASSWORD 'admin';**  
- **CREATE DATABASE financialdb;**  
- **GRANT ALL PRIVILEGES ON DATABASE financialdb TO postgres;**  
- **\q**  
- **exit**  
