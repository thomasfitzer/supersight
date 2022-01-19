DROP DATABASE IF EXISTS SuperSightDB;

CREATE DATABASE SuperSightDB;

USE SuperSightDB;

-- Hero/Villain
CREATE TABLE Hero(
	hero_id INT PRIMARY KEY AUTO_INCREMENT,
    is_hero BOOLEAN NOT NULL,
    name VARCHAR(50) NOT NULL,
	description VARCHAR(255)
);	

CREATE TABLE Superpower(
	super_power_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE Organization(
	org_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    is_hero BOOLEAN NOT NULL,
    description VARCHAR(255),
    address VARCHAR(255),
    contact VARCHAR(255)
);

CREATE TABLE Location(
	location_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    latitude DECIMAL(10,8) NOT NULL,
    longitude DECIMAL(11,8) NOT NULL,
    description VARCHAR(255),
    address VARCHAR(255)
);

CREATE TABLE HeroSuperpower(
	hero_id INT NOT NULL,
    super_power_id INT NOT NULL,
    PRIMARY KEY pk_HeroSuperpower(hero_id, super_power_id),
    FOREIGN KEY fk_HeroSuperpower_hero_id (hero_id)
		REFERENCES Hero(hero_id),
    FOREIGN KEY fk_HeroSuperpower_super_power_id (super_power_id)
		REFERENCES Superpower(super_power_id)
);

CREATE TABLE HeroOrganization(
	hero_id INT NOT NULL,
    org_id INT NOT NULL,
    PRIMARY KEY pk_HeroOrganization (hero_id, org_id),
    FOREIGN KEY fk_HeroOrganziation_hero_id (hero_id)
		REFERENCES Hero(hero_id),
	FOREIGN KEY fk_HeroOrganization_org_id (org_id)
		REFERENCES Organization (org_id)
);

CREATE TABLE Sighting(
	sight_id INT PRIMARY KEY AUTO_INCREMENT,
    hero_id INT NOT NULL,
    location_id INT NOT NULL,
    date DATETIME NOT NULL,
    FOREIGN KEY fk_Sighting_hero_id (hero_id)
		REFERENCES Hero(hero_id),
	FOREIGN KEY fk_Sighting_location_id (location_id)
		REFERENCES Location(location_id)
);

