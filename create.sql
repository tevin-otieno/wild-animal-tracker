CREATE DATABASE wildlife_tracker;
\c wildlife_tracker;
CREATE TABLE animals (id serial PRIMARY KEY, name varchar,category VARCHAR,health VARCHAR,age VARCHAR);
CREATE TABLE locations (id serial PRIMARY KEY,name VARCHAR);
CREATE TABLE locations_sightings (id serial PRIMARY KEY,location_id INT,sighting_id INT);
CREATE TABLE rangers (id serial PRIMARY KEY,name VARCHAR,badge_number VARCHAR,phone_number VARCHAR);
CREATE TABLE sightings (id serial PRIMARY KEY,animal_id INT,ranger_id INT,location_id INT,time TIMESTAMP);
CREATE TABLE rangers_sightings (id serial PRIMARY KEY,ranger_id INT,sighting_id INT);
CREATE DATABASE wildlife_tracker_test WITH TEMPLATE wildlife_tracker;
