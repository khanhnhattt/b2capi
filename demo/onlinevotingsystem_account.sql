CREATE database demo_1;
use demo_1;

create table student
(
	`UserId` int NOT NULL AUTO_INCREMENT,
  	`Username` varchar(45) NOT NULL,
  	`Password` varchar(45) NOT NULL,
  	PRIMARY KEY (`UserId`)
)