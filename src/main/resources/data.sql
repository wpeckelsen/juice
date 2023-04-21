-- Tool used for encrypting passwords to Bcrypt hash:
-- https://bcrypt-generator.com


-- PUBLISHER
INSERT INTO publisher (username, password)
VALUES ('PUBLISHER', '$2a$12$NAYyXIbMssrCrsMxS1Mz4.4arsyoEfQXh3PcvmyEHnm64BhQ/ZDTC');
INSERT INTO authority (username, authority)
VALUES ('PUBLISHER', 'ROLE_PUBLISHER');
-- username : PUBLISHER
-- password : PUBLISHER

-- DELETETHISPUBLISHER
INSERT INTO publisher (username, password)
VALUES ('DELETETHISPUBLISHER', '$2a$12$NAYyXIbMssrCrsMxS1Mz4.4arsyoEfQXh3PcvmyEHnm64BhQ/ZDTC');
INSERT INTO authority (username, authority)
VALUES ('DELETETHISPUBLISHER', 'ROLE_PUBLISHER');
-- username : DELETETHISPUBLISHER
-- password : PUBLISHER

-- CUSTOMER
INSERT INTO customer (username, password)
VALUES ('CUSTOMER', '$2a$12$zfOheQ2U1alXM5HLQWFz....KZOM0YtnjHqroNN2qSNB1/TmAGWnC');
INSERT INTO authority (username, authority)
VALUES ('CUSTOMER', 'ROLE_CUSTOMER');
-- username : CUSTOMER
-- password : CUSTOMER

-- DELETETHISCUSTOMER
INSERT INTO customer (username, password)
VALUES ('DELETETHISCUSTOMER', '$2a$12$zfOheQ2U1alXM5HLQWFz....KZOM0YtnjHqroNN2qSNB1/TmAGWnC');
INSERT INTO authority (username, authority)
VALUES ('DELETETHISCUSTOMER', 'ROLE_CUSTOMER');
-- username : DELETETHISCUSTOMER
-- password : CUSTOMER

-- ADMIN
INSERT INTO customer (username, password)
VALUES ('ADMIN', '$2a$12$WJnj6/6qWLADlGPzMs6V1endH4zO2Qzbxb/6W6yQSTBvd/gzzCl9S');
INSERT INTO publisher (username, password)
VALUES ('ADMIN', '$2a$12$WJnj6/6qWLADlGPzMs6V1endH4zO2Qzbxb/6W6yQSTBvd/gzzCl9S');
INSERT INTO authority (username, authority)
VALUES ('ADMIN', 'ROLE_CUSTOMER');
INSERT INTO authority (username, authority)
VALUES ('ADMIN', 'ROLE_PUBLISHER');
INSERT INTO authority (username, authority)
VALUES ('ADMIN', 'ROLE_ADMIN');
-- username : ADMIN
-- password : ADMIN




-- orphan domain & bid for test purposes
INSERT INTO domain (domainID, name, TLD, category, price)
VALUES (40, 'Cavia-Fanpage', 'NL', 'Cavias', 200);

INSERT INTO domain (domainID, name, TLD, category, price)
VALUES (41, 'Cavia-Fanpage', 'NL', 'Cavias', 200);


INSERT INTO bid (bidID, deadline, topic, anchor, vernacular, words)
VALUES (40, '10-11-2022', 'Cavia gehaktballen', 'lees hier meer over cavia vleesvervangers', 'Dutch', 550);

INSERT INTO bid (bidID, deadline, topic, anchor, vernacular, words)
VALUES (41, '10-11-2022', 'Cavia gehaktballen', 'lees hier meer over cavia vleesvervangers', 'Dutch', 550);


INSERT INTO deal (dealID, price, deadline, payment, terms)
VALUES (40, 577, '12-03-2034', 'USD', 'deliver A$AP');

INSERT INTO deal (dealID, price, deadline, payment, terms)
VALUES (41, 577, '12-03-2034', 'USD', 'deliver A$AP');