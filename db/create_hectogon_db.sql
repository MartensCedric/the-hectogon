DROP DATABASE IF EXISTS hectogon;

CREATE DATABASE hectogon;

USE hectogon;

CREATE TABLE User
(
Id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
Username VARCHAR(20) NOT NULL,
Password VARCHAR(64) NOT NULL
);

ALTER TABLE User
	ALTER Password SET DEFAULT "0263c1c898b2712bca2eb199935b08c6e149d6461e41a12bc2aed4fedb93e6ee"
;

INSERT INTO `User` (`Username`, `Password`) VALUES
("Loomy", DEFAULT),
("WinterGuardian", DEFAULT),
("MrCrispMasterPimp", DEFAULT),
("Totodilo", '99ff0af01d419c4276c1dc252cec6ea16e9b68dc956789592209dc940523b0ee');