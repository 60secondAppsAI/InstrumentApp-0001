CREATE DATABASE IF NOT EXISTS InstrumentApp;

CREATE TABLE InstrumentApp.instruments (
    `instrument_id` INT AUTO_INCREMENT,
    `symbol` VARCHAR(255),
    `name` VARCHAR(255),
    `instrument_type` VARCHAR(255),
    `currency` VARCHAR(255),
    `status` VARCHAR(255),
PRIMARY KEY (instrument_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE InstrumentApp.instrument_identifiers (
    `instrument_identifier_id` INT AUTO_INCREMENT,
    `instrument_id` INT,
    `identifier_type` VARCHAR(255),
    `identifier_value` VARCHAR(255),
PRIMARY KEY (instrument_identifier_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE InstrumentApp.instrument_attributes (
    `instrument_attribute_id` INT AUTO_INCREMENT,
    `instrument_id` INT,
    `attribute_name` VARCHAR(255),
    `attribute_value` VARCHAR(255),
PRIMARY KEY (instrument_attribute_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE InstrumentApp.instrument_classifications (
    `instrument_classification_id` INT AUTO_INCREMENT,
    `instrument_id` INT,
    `sector` VARCHAR(255),
    `industry` VARCHAR(255),
    `asset_class` VARCHAR(255),
PRIMARY KEY (instrument_classification_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

USE drcvelocity;
