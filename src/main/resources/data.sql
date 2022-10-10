-- Tool used for encrypting passwords to Bcrypt hash: https://bcrypt-generator.com


INSERT INTO customer (username, email, enabled, password)
VALUES ('ADMIN', 'ADMIN@MAIL.COM', true, '$2a$12$glWL3pyii1UmVub88XYWIOOFqeSK3UkL9uBb2xSEO7jdIKRKvEjmq');

INSERT INTO owner (username, email, enabled, password)
VALUES ('ADMIN', 'ADMIN@MAIL.COM', true, '$2a$12$glWL3pyii1UmVub88XYWIOOFqeSK3UkL9uBb2xSEO7jdIKRKvEjmq');
-- username : ADMIN
-- password : ADMIN

INSERT INTO authority (username, authority)
VALUES ('ADMIN', 'ROLE_ADMIN');

INSERT INTO authority (username, authority)
VALUES ('ADMIN', 'ROLE_CUSTOMER');

INSERT INTO authority (username, authority)
VALUES ('ADMIN', 'ROLE_OWNER');



INSERT INTO customer (username, email, enabled, password)
VALUES ('CUSTOMER', 'CUSTOMER@MAIL.COM', true, '$2a$12$6hcJfTwGeb7W.QQBEJUUP.NfTdLFjlqtKSA7.qLVZDMR7N0qDGRbG');
-- username : CUSTOMER
-- password : CUSTOMER

INSERT INTO authority (username, authority)
VALUES ('CUSTOMER', 'ROLE_CUSTOMER');



INSERT INTO owner (username, email, enabled, password)
VALUES ('OWNER', 'OWNER@MAIL.COM', true, '$2a$12$/Zj./KqAexQCGD/hjYcyOOk3Xmn1BZZ6OAwvNVstURskFdXDcAAjW');
-- username : OWNER
-- password : OWNER

INSERT INTO authority (username, authority)
VALUES ('OWNER', 'ROLE_OWNER');


INSERT INTO domain (domainID, name, TLD, category, price)
VALUES (40, 'Cavia-Fanpage', 'NL', 'Cavias', 200);

INSERT INTO bid (bidID, deadline, topic, anchor, vernacular, words)
VALUES (40, '10-11-2022', 'Cavia gehaktballen', 'lees hier meer over cavia vleesvervangers', 'Dutch', 550);






