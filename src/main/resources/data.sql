








INSERT INTO customer    (username, email, enabled, password) VALUES ('ADMIN', 'ADMIN@MAIL.COM', true, '$2a$12$glWL3pyii1UmVub88XYWIOOFqeSK3UkL9uBb2xSEO7jdIKRKvEjmq');
INSERT INTO authority   (username, authority) values ('ADMIN', 'ROLE_ADMIN');
INSERT INTO authority   (username, authority) values ('ADMIN', 'ROLE_CUSTOMER');
INSERT INTO authority   (username, authority) values ('ADMIN', 'ROLE_PUBLISHER');
-- username : ADMIN
-- password : ADMIN


INSERT INTO customer     (username, email, enabled, password) VALUES ('CUSTOMER', 'CUSTOMER@MAIL.COM', true, '$2a$13$oSwCguz.ZrEvHMSKLmwyvuHKXlHCUBW7CIrikM2jLnudk5MrUDfQi');
INSERT INTO authority    (username, authority) values ('CUSTOMER', 'ROLE_CUSTOMER');
-- username : CUSTOMER
-- password : CUSTOMER

INSERT INTO customer     (username, email, enabled, password) VALUES ('PUBLISHER', 'PUBPLISHER@MAIL.COM', true, '$2a$12$DlWTJXrpfx.qYND2rzAtN.uPTgOZWfLAE2QiuF0cAkb4IQPSpSo0m');
INSERT INTO authority    (username, authority) values ('PUBLISHER', 'ROLE_PUBLISHER');
-- username : PUBLISHER
-- password : PUBLISHER

-- Tool used for encrypting passwords to Bcrypt hash: https://bcrypt-generator.com

INSERT INTO bid         (bidID, deadline, topic, anchor, vernacular, words) VALUES (40, '10-11-2022', 'Cavia gehaktballen', 'lees hier meer over cavia vleesvervangers', 'Dutch', 550);
INSERT INTO bid         (bidID, deadline, topic, anchor, vernacular, words) VALUES (41, '10-12-2022', 'kazen', 'sommige kazen zijn niet te vreten', 'Dutch', 550);


INSERT INTO domain       (domainID, name, TLD, category, price) VALUES (40, 'Cavia-Fanpage', 'NL', 'Cavias', 200);
INSERT INTO domain       (domainID, name, TLD, category, price) VALUES (41, 'Cavia-Hatepage', 'NL', 'Hater', 180);

INSERT INTO publisher    (publisherID, name, country, niche) VALUES (40, 'Kees de Cavia', 'Netherlands', 'cavia lover');
INSERT INTO publisher    (publisherID, name, country, niche) VALUES (41, 'Hans de AntiCavia', 'Netherlands', 'Hater');

-- INSERT INTO publisher   (publisherID, name, country, niche, domains) VALUES ('300', 'Geit', 'Nederland', 'Lifestyle', '{"1", "2", "3"}');

-- INSERT INTO domain       (domainID, name, TLD, category, price) values ('2', 'autonieuws', 'NL', 'Cars', 30);
-- INSERT INTO domain       (domainID, name, TLD, category, price) values ('3', 'autonieuws', 'NL', 'Cars', 30);



