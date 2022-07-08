







INSERT INTO customer    (username, email, enabled, password) VALUES ('ADMIN', 'ADMIN@MAIL.COM', true, '$2a$12$glWL3pyii1UmVub88XYWIOOFqeSK3UkL9uBb2xSEO7jdIKRKvEjmq');
INSERT INTO authority   (username, authority) values ('ADMIN', 'ROLE_ADMIN');
INSERT INTO authority   (username, authority) values ('ADMIN', 'ROLE_CLIENT');
-- username : ADMIN
-- password : ADMIN


INSERT INTO customer     (username, email, enabled, password) VALUES ('CLIENT', 'CLIENT@MAIL.COM', true, '$2a$12$jTDRjsTkz3mRAVrLqpi1LO5onRPcXsTnt7M2nbH26eRmCtsYb1kLy');
INSERT INTO authority   (username, authority) values ('CLIENT', 'ROLE_CLIENT');
-- username : CLIENT
-- password : CLIENT


-- INSERT INTO customer     (username, email, enabled, password) VALUES ('PUBLISHER', 'PUBLISHER@MAIL.COM', true, '$2a$12$OWn2Qlf163MBzY5iD7DNAerTMlYawoB1y3IZRf7equ4slrjp9jJcy');
-- INSERT INTO authority   (username, authority) values ('PUBLISHER', 'ROLE_PUBLISHER');
-- username : PUBLISHER
-- password : PUBLISHER

