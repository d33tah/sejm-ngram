DROP DATABASE IF EXISTS `wystapienia`;

CREATE DATABASE `wystapienia` DEFAULT CHARACTER SET utf8;

USE `wystapienia`;

GRANT ALL ON wystapienia.* TO 'testuser'@'localhost';




CREATE TABLE `ngrams` (
    `id`             INT(10) NOT NULL AUTO_INCREMENT,
	`ngram`		        VARCHAR(100) NOT NULL,
	`posel` 		    VARCHAR(40),
	`stanowisko` 		VARCHAR(40),
	`data` 			    DATE,
    `czlowiek_id`       INT(10),
	`klub_id` 	        INT(10),
	PRIMARY KEY(id)
);



#Storage for data downloaded from http://sejmometr.pl/sejm_wystapienia/id where id 1..
CREATE TABLE `html_wystapienia` (
    `id` INT(10) NOT NULL,
	`posel` 		    VARCHAR(40),
	`stanowisko`		VARCHAR(50),
    `tekst`             TEXT,
    `status`            INT(10) default 0,
	PRIMARY KEY(id)
);

#Storage for table from http://sejmometr.pl/api/docs/ep_Sejm_Wystapienie (dataset sejm_wystapienia)
CREATE TABLE `sejm_wystapienia` (
 `id` INT(10) NOT NULL,
 `data` DATE,
 `posiedzenie_id` INT(10),
 `czlowiek_id` INT(10),
 `debata_id` INT(10),
 `dzien_sejmowy_id` INT(10),
 `ilosc_slow` INT(10),
 `klub_id` INT(10),
 `kolejnosc` INT(10),
 `punkt_id` INT(10),
 `skrot` TEXT,
 `stanowisko_id` INT(10),
 `video` INT(10),
 `tytul` VARCHAR(100),
 `status`            INT(10) default 0,
 PRIMARY KEY(id)
)

