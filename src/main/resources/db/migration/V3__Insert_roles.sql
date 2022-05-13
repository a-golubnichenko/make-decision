INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_MODERATOR');
INSERT INTO roles (name) VALUES ('ROLE_USER');

-- assign the role ROLE_ADMIN to the admin user
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);