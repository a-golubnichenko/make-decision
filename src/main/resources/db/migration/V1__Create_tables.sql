CREATE TABLE users
(
   id BIGSERIAL PRIMARY KEY,
   username VARCHAR(250) NOT NULL UNIQUE,
   password VARCHAR(250) NOT NULL,
   first_name VARCHAR(250) NOT NULL,
   last_name VARCHAR(250) NOT NULL,
   email VARCHAR(250) NOT NULL UNIQUE,
   created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles
(
   id BIGSERIAL PRIMARY KEY,
   name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE user_roles
(
   user_id BIGSERIAL,
   role_id BIGSERIAL,
   UNIQUE (user_id, role_id)
);