-- Utiliser le schéma `demo` pour les opérations
USE demo;

-- Réinitialiser et vider les tables
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE bidlist;
TRUNCATE TABLE curvepoint;
TRUNCATE TABLE rating;
TRUNCATE TABLE rulename;
TRUNCATE TABLE trade;
TRUNCATE TABLE users;

-- Réactiver les clés étrangères
SET FOREIGN_KEY_CHECKS = 1;

-- Insérer des données
INSERT INTO BidList(account, type, bidQuantity) VALUES ('user', 'type', 10);
INSERT INTO Users(fullname, username, password, role) VALUES('Administrator', 'admin', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'ADMIN');
INSERT INTO Users(fullname, username, password, role) VALUES('User', 'user', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'USER');