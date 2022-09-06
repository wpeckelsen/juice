







INSERT INTO customer    (username, email, enabled, password) VALUES ('ADMIN', 'ADMIN@MAIL.COM', true, '$2a$12$glWL3pyii1UmVub88XYWIOOFqeSK3UkL9uBb2xSEO7jdIKRKvEjmq');
INSERT INTO authority   (username, authority) values ('ADMIN', 'ROLE_ADMIN');
INSERT INTO authority   (username, authority) values ('ADMIN', 'ROLE_CUSTOMER');
-- username : ADMIN
-- password : ADMIN


INSERT INTO customer     (username, email, enabled, password) VALUES ('CUSTOMER1', 'CUSTOMER@MAIL.COM', true, '$2a$12$PEoiMgriMENEt0yvWo9IauSJrfV3gFbxuatq2DkNw7.TXqzeRhE8W');
INSERT INTO authority    (username, authority) values ('CUSTOMER1', 'ROLE_CUSTOMER');
-- username : CUSTOMER1
-- password : CUSTOMER1

INSERT INTO customer     (username, email, enabled, password) VALUES ('CUSTOMER2', 'CUSTOMER@MAIL.COM', true, '$2a$12$AkvuBM8HMEFG3XhwqIlk9OP1DQOkBJHmq8UTz0BvLIKrMbOVRPHYq');
INSERT INTO authority    (username, authority) values ('CUSTOMER2', 'ROLE_CUSTOMER');
-- username : CUSTOMER2
-- password : CUSTOMER2


-- INSERT INTO publisher   (publisherID, name, country, niche, domains) VALUES ('300', 'Geit', 'Nederland', 'Lifestyle', '{"1", "2", "3"}');

-- INSERT INTO domain       (domainID, name, TLD, category, price) values ('1', 'autonieuws', 'NL', 'Cars', 30);
-- INSERT INTO domain       (domainID, name, TLD, category, price) values ('2', 'autonieuws', 'NL', 'Cars', 30);
-- INSERT INTO domain       (domainID, name, TLD, category, price) values ('3', 'autonieuws', 'NL', 'Cars', 30);


