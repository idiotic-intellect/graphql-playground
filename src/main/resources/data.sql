DROP TABLE IF EXISTS customer;

CREATE TABLE customer(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, 
	name VARCHAR(50), 
	age INT, 
	city VARCHAR(50)
);

INSERT INTO customer(name, age, city) 
VALUES 
	('Ram', 45, 'Trichy'), 
	('Ravi', 35, 'Chennai'),
	('Sura', 25, 'Bengaluru'),
	('Rag', 15, 'Ohio'),
	('Raghav', 20, 'Toronto');
