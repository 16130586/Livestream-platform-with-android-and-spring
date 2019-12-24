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
INSERT INTO `pay_subscription` VALUES (1,2,1,'2019-11-30 12:12:59','2020-11-30 12:12:59',18);
/*!40000 ALTER TABLE `pay_subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `stream`
--

LOCK TABLES `stream` WRITE;
/*!40000 ALTER TABLE `stream` DISABLE KEYS */;
INSERT INTO `stream` VALUES (1,'1',1,'https://images.unsplash.com/photo-1494935362342-566c6d6e75b5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjExMjU4fQ&dpr=1&auto=format&fit=crop&w=416&h=312&q=60',344,'2019-12-19 12:00:00',NULL,1,'asda','asda','zxczc','asdasd',11,'asdasd','new','Cày TOP 1 TĐ TFT','play_back_url'),(2,'2',3,'https://images.unsplash.com/photo-1494935362342-566c6d6e75b5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjExMjU4fQ&dpr=1&auto=format&fit=crop&w=416&h=312&q=60',231,'2019-12-19 12:00:00',NULL,-1,'asd','asdasd','adsas','asdasd',2,'asd','asd','Học cách sống giữa đảo hoang','play_back_url'),(3,'3',2,'https://images.unsplash.com/photo-1494935362342-566c6d6e75b5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjExMjU4fQ&dpr=1&auto=format&fit=crop&w=416&h=312&q=60',231,'2019-12-19 12:00:00',NULL,1,'asd','asd','asd','asd',12,'asd','asd','Thử thách làm chó 24h | nhocsockute','play_back_url'),(4,'4',4,'https://images.unsplash.com/photo-1494935362342-566c6d6e75b5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjExMjU4fQ&dpr=1&auto=format&fit=crop&w=416&h=312&q=60',231,'2019-12-19 12:00:00',NULL,-1,'asd','asd','asd','asd',12,NULL,'asferg','Cuộc sống nhàm chán, viết linh tinh','play_back_url'),(5,'5',5,'https://images.unsplash.com/photo-1494935362342-566c6d6e75b5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjExMjU4fQ&dpr=1&auto=format&fit=crop&w=416&h=312&q=60',23,'2019-12-19 12:00:00',NULL,1,NULL,NULL,NULL,NULL,12,NULL,'asrg','Alo alo, you can\'t see me.','play_back_url'),(6,'6',6,'https://images.unsplash.com/photo-1494935362342-566c6d6e75b5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjExMjU4fQ&dpr=1&auto=format&fit=crop&w=416&h=312&q=60',123,'2019-12-19 12:00:00',NULL,-1,NULL,NULL,NULL,NULL,12,NULL,'feasddfg','Nah? Click to see','play_back_url'),(7,'7',7,'https://images.unsplash.com/photo-1494935362342-566c6d6e75b5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjExMjU4fQ&dpr=1&auto=format&fit=crop&w=416&h=312&q=60',123,'2019-12-19 12:00:00',NULL,1,NULL,NULL,NULL,NULL,12,NULL,'asdwrqwer','Một cộng một bằng ba, ok?','play_back_url'),(8,'q5pg7ffx',8,'https://images.unsplash.com/photo-1494935362342-566c6d6e75b5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjExMjU4fQ&dpr=1&auto=format&fit=crop&w=416&h=312&q=60',213,'2019-12-19 12:00:00',NULL,-1,NULL,NULL,NULL,'rtmp://6969e5.entrypoint.cloud.wowza.com/app-facd',1935,'/app-facd','whguffyc','Livestream with hot boy','https://wowzaprod272-i.akamaihd.net/hls/live/1005215/ead88fa0/playlist.m3u8'),(9,'q5pg7ffx',9,'https://images.unsplash.com/photo-1494935362342-566c6d6e75b5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjExMjU4fQ&dpr=1&auto=format&fit=crop&w=416&h=312&q=60',343,'2019-12-19 12:00:00',NULL,1,NULL,NULL,NULL,'rtmp://6969e5.entrypoint.cloud.wowza.com/app-facd',1935,'/app-facd','whguffyc','Kameyokoooooooooooo','https://wowzaprod272-i.akamaihd.net/hls/live/1005215/ead88fa0/playlist.m3u8'),(10,'q5pg7ffx',1,'https://images.unsplash.com/photo-1494935362342-566c6d6e75b5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjExMjU4fQ&dpr=1&auto=format&fit=crop&w=416&h=312&q=60',54,'2019-12-19 12:00:00',NULL,-1,NULL,NULL,NULL,'rtmp://6969e5.entrypoint.cloud.wowza.com/app-facd',1935,'/app-facd','dffg','Hết biết đặt tên rồi','https://wowzaprod272-i.akamaihd.net/hls/live/1005215/ead88fa0/playlist.m3u8');
/*!40000 ALTER TABLE `stream` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `stream_type`
--

LOCK TABLES `stream_type` WRITE;
/*!40000 ALTER TABLE `stream_type` DISABLE KEYS */;
INSERT INTO `stream_type` VALUES (1,1),(1,3),(1,4),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(7,2),(6,3),(4,3),(5,2),(3,2),(2,3),(8,2),(8,4),(9,2),(9,4),(10,1),(10,2),(10,3);
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
INSERT INTO `types` VALUES (1,'game'),(2,'music'),(3,'social'),(4,'knowledge');
/*!40000 ALTER TABLE `types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'q5pg7ffx','asd','$2a$10$ajDxCUIia9CMeikwNKxr.eraoC17YuYYKhtgpwfA18qkjHgirpoai','Nhok Tuyen','/images/ava_default.jpg','mnit012899@gmail.com','',9999),(2,NULL,'qwe','$2a$10$M/X3M8kPIbk.GyFGrB9Zne2zJ6/Y.pEgMUdHo.6PmtYbnJXFS.MQC','Cat kun','/images/ava_default.jpg','zxc','zxc',1),(3,NULL,'zxc','$2a$10$M/X3M8kPIbk.GyFGrB9Zne2zJ6/Y.pEgMUdHo.6PmtYbnJXFS.MQC','Nhok Sock','/images/ava_default.jpg','mnit@gmail.com',NULL,NULL),(4,NULL,'rty','$2a$10$QNgZzqMMQ53RVqlkSCB2nuyx1NPFWPG8oI5PBQEHa6bS0zic5I4iy','Kute xxx','/images/ava_default.jpg','abc@gmail.com',NULL,NULL),(5,NULL,'uio','$2a$10$2k36oS/etaKa4P0wIXvAm./MV.utboOknuWaU7QILo6sOJFciQb3O','Chooi Soong Boo','/images/ava_default.jpg','dfg@gh.com',NULL,0),(6,NULL,'mnb','$2a$10$kue3Dvmos4ARTU72oRlxou4sW.ItBTjTSO46lzy.2nsTfWyw6rlp6','Tsuyan','/images/ava_default.jpg','nhoktuyenpro@gmail.com',NULL,0),(7,NULL,'cvb','$2a$10$Wz7i3Q8ueAmSaE3nKpsa5eZ/Csez6CVrP7a8.o9YymKhE3ODPUdJm','Đình Tuyên','/images/ava_default.jpg','hdhddu@hfu.com',NULL,0),(8,NULL,'fgh','$2a$10$kxQxkHHHAp6wzasLtS/qZeJwSIfRQ2VGlAgywUKHaLpHr2j1hhmoq','Hot boy','/images/ava_default.jpg','fuguguc@ufhck.com',NULL,0),(9,NULL,'jkl','$2a$10$hEQJ1XHkC7AiQSSSTuJWVO6Vcb0pmmFemGm7Vnt5c.ljk19ghSJTu','udyfyfucfug','/images/ava_default.jpg','fuguguc@ufhck.com',NULL,0);
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

-- Dump completed on 2019-12-23 23:04:31
