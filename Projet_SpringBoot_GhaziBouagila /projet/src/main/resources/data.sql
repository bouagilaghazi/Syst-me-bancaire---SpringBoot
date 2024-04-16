DELETE FROM client;
-- Insertion de 10 clients
INSERT INTO client (nom, prenom, email, date_naissance, date_inscription)
VALUES ('Dupont', 'Jean', 'jean.dupont@example.com', '1990-01-01', '2022-01-17');

INSERT INTO client (nom, prenom, email, date_naissance, date_inscription)
VALUES ('Martin', 'Sophie', 'sophie.martin@example.com', '1985-03-15', '2022-01-17');

INSERT INTO client (nom, prenom, email, date_naissance, date_inscription)
VALUES ('Lefevre', 'Paul', 'paul.lefevre@example.com', '1978-11-20', '2022-01-17');

INSERT INTO client (nom, prenom, email, date_naissance, date_inscription)
VALUES ('Dubois', 'Isabelle', 'isabelle.dubois@example.com', '1995-07-08', '2022-01-17');

INSERT INTO client (nom, prenom, email, date_naissance, date_inscription)
VALUES ('Girard', 'Pierre', 'pierre.girard@example.com', '1982-09-25', '2022-01-17');

INSERT INTO client (nom, prenom, email, date_naissance, date_inscription)
VALUES ('Leroux', 'Marie', 'marie.leroux@example.com', '1980-04-12', '2022-01-17');

INSERT INTO client (nom, prenom, email, date_naissance, date_inscription)
VALUES ('Moreau', 'Luc', 'luc.moreau@example.com', '1993-06-30', '2022-01-17');

INSERT INTO client (nom, prenom, email, date_naissance, date_inscription)
VALUES ('Roche', 'Catherine', 'catherine.roche@example.com', '1975-02-18', '2022-01-17');

INSERT INTO client (nom, prenom, email, date_naissance, date_inscription)
VALUES ('Fournier', 'Michel', 'michel.fournier@example.com', '1987-10-05', '2022-01-17');

INSERT INTO client (nom, prenom, email, date_naissance, date_inscription)
VALUES ('Caron', 'Sophie', 'sophie.caron@example.com', '1998-12-03', '2022-01-17');

INSERT INTO compte (IBAN, solde, intitule_compte, type_compte,date_creation ) VALUES ('FR7630004000031234567890126', 1000.00, 'Compte Courant', 'COMPTE_COURANT_SIMPLE','2022-01-17');
INSERT INTO compte (IBAN, solde, intitule_compte, type_compte,date_creation) VALUES ('FR7630004000031234567894146', 1000.00, 'Compte2 Courant', 'COMPTE_COURANT_SIMPLE','2024-07-07');
INSERT INTO compte (IBAN, solde, intitule_compte, type_compte,date_creation) VALUES ('FR7630404000031234567890127', 1000.00, 'Compte Courant', 'COMPTE_COURANT_JOINT','2022-08-07');
INSERT INTO compte (IBAN, solde, intitule_compte, type_compte,date_creation) VALUES ('FR7630704000031234567894147', 1000.00, 'Compte2 Courant', 'COMPTE_COURANT_JOINT','2022-012-17');
INSERT INTO titulaire_compte (id_client, IBAN) VALUES (1, 'FR7630004000031234567890126');
INSERT INTO titulaire_compte (id_client, IBAN) VALUES (2, 'FR7630004000031234567894146');
INSERT INTO titulaire_compte (id_client, IBAN) VALUES (3, 'FR7630404000031234567890127');
INSERT INTO titulaire_compte (id_client, IBAN) VALUES (4, 'FR7630704000031234567894147');
-- Insertion de transactions
INSERT INTO transaction ( montant, type_transaction, type_source, id_source, IBAN)
VALUES ( 100.00, 'DEBIT', 'Virement', 'FR7630004000031234567890126', 'FR7630004000031234567894146');

INSERT INTO transaction (montant, type_transaction, type_source, id_source, IBAN)
VALUES (200.00, 'CREDIT', 'Virement', 'FR7630004000031234567894146', 'FR7630004000031234567890126');

INSERT INTO carte_bancaire (numero_Carte, Code_Secret, date_Expiration, titulaire_compte, IBAN)
VALUES ('1234567890123456', '1234', '2024-12-31', 1, 'FR7630004000031234567890126');


-- Path: src/main/resources/data.sql

