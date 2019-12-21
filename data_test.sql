-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: livestream
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,1,1,'team minh chill phet',NULL,1,1,NULL),(2,1,1,'nah nah nha','2019-11-24 14:28:50',NULL,1,NULL),(3,1,1,'nah nah nha','2019-11-25 06:29:00',0,1,150),(4,1,1,'nah nah nha','2019-11-25 06:41:03',0,1,1550),(5,1,1,'nah nah nha','2019-12-06 06:48:04',0,1,1650);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `favourite_saved`
--

LOCK TABLES `favourite_saved` WRITE;
/*!40000 ALTER TABLE `favourite_saved` DISABLE KEYS */;
INSERT INTO `favourite_saved` VALUES (1,1,1,NULL,1);
/*!40000 ALTER TABLE `favourite_saved` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (1,1,'hút cần ei',1,NULL);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `notification_user`
--

LOCK TABLES `notification_user` WRITE;
/*!40000 ALTER TABLE `notification_user` DISABLE KEYS */;
INSERT INTO `notification_user` VALUES (1,1);
/*!40000 ALTER TABLE `notification_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `pay_subscription`
--

LOCK TABLES `pay_subscription` WRITE;
/*!40000 ALTER TABLE `pay_subscription` DISABLE KEYS */;
INSERT INTO `pay_subscription` VALUES (1,2,1,NULL,'2019-11-30 12:12:59',600),(2,1,2,NULL,NULL,100);
/*!40000 ALTER TABLE `pay_subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `stream`
--

LOCK TABLES `stream` WRITE;
/*!40000 ALTER TABLE `stream` DISABLE KEYS */;
INSERT INTO `stream` VALUES (1,'1',1,null,344,NULL,NULL,0,'asda','asda','zxczc','asdasd',11,'asdasd','asdasdasd','asd','play_back_url'),(2,'2',3,null,231,NULL,NULL,0,'asd','asdasd','adsas','asdasd',2,'asd','asd','asd','play_back_url'),(3,'3',1,null,231,NULL,NULL,0,'asd','asd','asd','asd',12,'asd','asd','asd','play_back_url'),(4,'4',1,null,231,NULL,NULL,0,'asd','asd','asd','asd',12,NULL,'asferg','asd','play_back_url'),(5,'5',1,null,23,NULL,NULL,0,NULL,NULL,NULL,NULL,12,NULL,'asrg','asd','play_back_url'),(6,'6',1,null,123,NULL,NULL,0,NULL,NULL,NULL,NULL,12,NULL,'feasddfg','asd','play_back_url'),(7,'7',1,null,123,NULL,NULL,0,NULL,NULL,NULL,NULL,12,NULL,'asdwrqwer','asd','play_back_url');
/*!40000 ALTER TABLE `stream` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `stream_type`
--

LOCK TABLES `stream_type` WRITE;
/*!40000 ALTER TABLE `stream_type` DISABLE KEYS */;
INSERT INTO `stream_type` VALUES (1,1),(1,3),(1,4),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(7,2),(6,3),(4,3),(5,2),(3,2),(2,3);
/*!40000 ALTER TABLE `stream_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `subscriber`
--

LOCK TABLES `subscriber` WRITE;
/*!40000 ALTER TABLE `subscriber` DISABLE KEYS */;
INSERT INTO `subscriber` VALUES (2,1);
/*!40000 ALTER TABLE `subscriber` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
INSERT INTO `subscription` VALUES (1,'premium',1,100),(2,'VIP',2,300);
/*!40000 ALTER TABLE `subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `types`
--

LOCK TABLES `types` WRITE;
/*!40000 ALTER TABLE `types` DISABLE KEYS */;
INSERT INTO `types` VALUES (1,'adult'),(2,'hen'),(3,'milf'),(4,'shota');
/*!40000 ALTER TABLE `types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,Null,'asd','$2a$10$ajDxCUIia9CMeikwNKxr.eraoC17YuYYKhtgpwfA18qkjHgirpoai','asd',N'Đây là phần giới thiệu của chúng tôi , rất vui đc làm quen với các bạn','mnit012899@gmail.com','',9999,NULL,NULL),
(2,Null,'zxc','$2a$10$M/X3M8kPIbk.GyFGrB9Zne2zJ6/Y.pEgMUdHo.6PmtYbnJXFS.MQC','zxc',N'Đây là phần giới thiệu của chúng tôi , rất vui đc làm quen với các bạn','zxc','zxc',1,NULL,NULL),
(3,Null,'aspiration','$2a$10$M/X3M8kPIbk.GyFGrB9Zne2zJ6/Y.pEgMUdHo.6PmtYbnJXFS.MQC',NULL,N'Đây là phần giới thiệu của chúng tôi , rất vui đc làm quen với các bạn','mnit@gmail.com',NULL,NULL,NULL,NULL),
(4,Null,'asdasdasd','$2a$10$QNgZzqMMQ53RVqlkSCB2nuyx1NPFWPG8oI5PBQEHa6bS0zic5I4iy',NULL,N'Đây là phần giới thiệu của chúng tôi , rất vui đc làm quen với các bạn','abc@gmail.com',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_favourite`
--

LOCK TABLES `user_favourite` WRITE;
/*!40000 ALTER TABLE `user_favourite` DISABLE KEYS */;
INSERT INTO `user_favourite` VALUES (1,3),(1,1);
/*!40000 ALTER TABLE `user_favourite` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-09  9:56:54
