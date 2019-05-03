CREATE DATABASE  IF NOT EXISTS `flipkart` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `flipkart`;

-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: letbeclear
-- ------------------------------------------------------
-- Server version	5.5.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for `user`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `user` (
	`USER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`REGISTER_TYPE` varchar(4) DEFAULT NULL,
	`PROFILE_TYPE` varchar(32) DEFAULT NULL,
	`REGISTRATION_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`LAST_SESSION` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
	`PASSWORD_EXPIRED` smallint(6) DEFAULT '-1',
	`FIELD1` smallint(5) DEFAULT NULL,
	`FIELD2` int(11) DEFAULT NULL,
	`FIELD3` bigint(20) DEFAULT NULL,
	`FIELD4` varchar(256) DEFAULT NULL,
	`FIELD5` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00', 
	PRIMARY KEY(`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data in table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` VALUES (1000, 'USER', 'USER', '2019-04-16 06:19:10', '2019-04-16 06:19:10', '-1', NULL, NULL, NULL, NULL, '2019-04-16 06:19:10');
INSERT INTO `user` VALUES (1001, 'USER', 'USER', '2019-04-16 06:19:10', '2019-04-16 06:19:10', '-1', NULL, NULL, NULL, NULL, '2019-04-16 06:19:10');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for `user_registration`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `user_registration`(
	`USER_ID` bigint(20) NOT NULL,
	`USERNAME` varchar(128) NOT NULL,
	`PASSWORD` varchar(128) NOT NULL,
	`PASSWORD_INVALID` smallint(6) DEFAULT '-1',
	`RESET_PASSWORD_CODE` varchar(128) DEFAULT NULL,
	`TIMEOUT` smallint(6) DEFAULT '-1',
	`PASSWORD_CREATION_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
	`RESET_PASSWORD_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
	`FIELD1` smallint(6) DEFAULT NULL,
	`FIELD2` int(11) DEFAULT NULL,
	`FIELD3` varchar(256) DEFAULT NULL,
	`FIELD4` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
	PRIMARY KEY (`USER_ID`),
	CONSTRAINT `USERNAME` UNIQUE (`USERNAME`),
	CONSTRAINT `userreg_user_key1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data in table `user_registration`
--

LOCK TABLES `user_registration` WRITE;
/*!40000 ALTER TABLE `user_registration` DISABLE KEYS */;

INSERT INTO `user_registration` VALUES ('1000', 'abc@gmail.com',  'abc@123', '-1', 'RESETPASS', '-1', '2019-04-17 07:37:10', '2019-04-17 07:37:10', NULL, NULL, NULL, '2019-04-17 07:37:10');
INSERT INTO `user_registration` VALUES ('1001', 'abcd@gmail.com','abcd@123', '-1', 'RESETPASS', '-1', '2019-04-17 07:37:10', '2019-04-17 07:37:10', NULL, NULL, NULL, '2019-04-17 07:37:10');

/*!40000 ALTER TABLE `user_registration` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for `role`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `role`(
	`ROLE_ID` bigint(20) NOT NULL,
	`USER_ROLE` varchar(128) DEFAULT NULL,
	PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data in table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;

INSERT INTO `role` VALUES ('2000', 'USER');
INSERT INTO `role` VALUES ('2001', 'PREMIUM_MEMBER');
INSERT INTO `role` VALUES ('2002', 'ADMIN');

/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;



--
-- Table structure for `user_role`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `user_role`(
	`USER_ID` bigint(20) NOT NULL,
	`ROLE_ID` bigint(20) NOT NULL,
	CONSTRAINT `user_role_key1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`) ON DELETE CASCADE,
	CONSTRAINT `user_role_key2` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ROLE_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data in table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;

INSERT INTO `user_role` VALUES (1000, 2000);
INSERT INTO `user_role` VALUES (1001, 2000);
INSERT INTO `user_role` VALUES (1001, 2002);

/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;



/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


