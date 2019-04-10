CREATE TABLE `STUDENT`(
	`STUDENT_ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`NAME` VARCHAR(128) DEFAULT NULL,
	`AGE` TINYINT(6),
	PRIMARY KEY (`STUDENT_ID`)
)ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

CREATE TABLE `PRODUCT`(
	`PRODUCT_ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`PRODUCT_NAME` VARCHAR(128) DEFAULT NULL,
	PRIMARY KEY (`PRODUCT_ID`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;