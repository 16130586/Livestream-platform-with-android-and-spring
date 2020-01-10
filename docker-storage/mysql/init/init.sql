create user if not EXISTS 'maxwell'@'%' IDENTIFIED BY 'maxwell';
ALTER USER 'maxwell'@'%' IDENTIFIED WITH mysql_native_password BY 'maxwell';
GRANT ALL on *.* to 'maxwell'@'%';
GRANT SELECT, REPLICATION CLIENT, REPLICATION SLAVE on *.* to 'maxwell'@'%';
flush privileges;

CREATE SCHEMA IF NOT EXISTS `livestream`;
USE `livestream`;

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `wowza_id` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `gmail` varchar(255) DEFAULT NULL,
  `forgot_token` varchar(255) DEFAULT NULL,
  `subscribe_total` int(11) DEFAULT NULL,
  `avatar` text,
  `background` text,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'zg9t6qvx','asd','$2a$10$ajDxCUIia9CMeikwNKxr.eraoC17YuYYKhtgpwfA18qkjHgirpoai','The Soul of Wind','The Soul of Wind is music label for a large variety of Music. We provide the best services to the pianist, musician, artist. ','mnit012899@gmail.com','',1,'https://ephoto360.com/uploads/worigin/2019/12/07/Che_poster_Mat_Biec_voi_anh_cua_ban5deb3278a7099_7691234d266c5bfd29f558b82a462301.jpg','https://images.unsplash.com/photo-1428189923803-e9801d464d76?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60'),(2,'','qwerty','$2a$10$ZtliVLmI67J9kyOJEIo.F.ogBpzAfQIeuCDDhTaFp45WCDAFxPKgG','Daddy Yankee','- For Release your music on music platform: Itunes, Spotify . . . ','mnit012899@gmail.com',NULL,0,'/images/ava_default4.jpg','https://images.unsplash.com/photo-1498614147815-44204cb1214d?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60'),(3,NULL,'dinhtuyen','$2a$10$ajDxCUIia9CMeikwNKxr.eraoC17YuYYKhtgpwfA18qkjHgirpoai','Tar Network','- Adding any kind of information which belongs to a the video we\'ve uploaded (missing information of the music or visual.)','mnit012899@gmail.com','',0,'/images/ava_default5.jpg','https://images.unsplash.com/photo-1475414149481-0ee5f7a27ea1?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60'),(4,NULL,'ngocthat','$2a$10$ajDxCUIia9CMeikwNKxr.eraoC17YuYYKhtgpwfA18qkjHgirpoai','HG Entertainment','- Send us a mail at: justinjetzorbas94@gmail.com (Our Submissions Manager) Or TheSoulofWind@outlook.com','mnit012899@gmail.com',NULL,0,'/images/ava_default6.jpg','https://images.unsplash.com/photo-1492269682833-cd80f8a20b08?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS `stream`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `stream` (
  `stream_id` int(11) NOT NULL AUTO_INCREMENT,
  `wowza_id` varchar(255) DEFAULT NULL,
  `owner_id` int(11) DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `total_view` int(11) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `stream_status` int(11) DEFAULT NULL,
  `forwards` text,
  `forwards_url` text,
  `stored_url` text,
  `primary_server_url` text,
  `host_port` int(11) DEFAULT NULL,
  `application` text,
  `stream_name` text,
  `title` text,
  `hls_play_back_url` text,
  `like_count` int(11) DEFAULT '0',
  PRIMARY KEY (`stream_id`),
  KEY `owner_id` (`owner_id`),
  CONSTRAINT `stream_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stream`
--

LOCK TABLES `stream` WRITE;
/*!40000 ALTER TABLE `stream` DISABLE KEYS */;
INSERT INTO `stream` VALUES (1,'1',1,'https://images.unsplash.com/photo-1500326252658-34e36edef030?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60',452,'2019-12-24 01:20:36','2019-12-24 02:20:36',1,NULL,NULL,'http://techslides.com/demos/sample-videos/small.mp4','12312312',1232,'21ada','assadasdas','Halsey - Without you','http://techslides.com/demos/sample-videos/small.mp4',0),(7,'krtv0tsv',2,'https://images.unsplash.com/photo-1488841585398-132c573f7aa2?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60',326,'2019-12-23 01:20:36','2019-12-23 02:20:36',2,NULL,NULL,'http://techslides.com/demos/sample-videos/small.mp4','https://wowzaprod272-i.akamaihd.net/hls/live/1005215/42f2da20/playlist.m3u8',1935,'app-7446','04e875b0','Believer - Image Dragons - Guitar Fingerstyle Cover','http://techslides.com/demos/sample-videos/small.mp4',0),(157,'zg9t6qvx',3,'https://images.unsplash.com/photo-1501556424050-d4816356b73e?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60',698,'2019-12-25 01:20:36','2019-12-25 01:32:18',2,NULL,NULL,'http://techslides.com/demos/sample-videos/small.mp4','rtmp://fa369d.entrypoint.cloud.wowza.com/app-7446',1935,'app-7446','04e875b0','[Vietsub+Lyrics] All Falls Down - Alan Walker ft Noah Cyrus, Digital Farm Animals','http://techslides.com/demos/sample-videos/small.mp4',1),(158,NULL,4,'https://images.unsplash.com/photo-1485594050903-8e8ee7b071a8?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60',235,'2019-12-22 01:20:36','2019-12-22 01:30:36',2,NULL,NULL,'http://techslides.com/demos/sample-videos/small.mp4','rtmp://fa369d.entrypoint.cloud.wowza.com/app-7446',1935,'app-7446','04e875b0','[Vietsub + Pinyin] Dũng Khí - Miên Tử | 勇气 - 棉子 (Tik Tok/抖音)','http://techslides.com/demos/sample-videos/small.mp4',0),(159,NULL,1,'https://images.unsplash.com/photo-1475257026007-0753d5429e10?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60',526,'2019-12-21 01:20:36','2019-12-21 01:30:36',2,NULL,NULL,'http://techslides.com/demos/sample-videos/small.mp4','rtmp://fa369d.entrypoint.cloud.wowza.com/app-7446',1935,'app-7446','04e875b0','Halsey - Gasoline (Official Audio)','http://techslides.com/demos/sample-videos/small.mp4',0);
/*!40000 ALTER TABLE `stream` ENABLE KEYS */;
UNLOCK TABLES;



DROP TABLE IF EXISTS `types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `types` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `types`
--

LOCK TABLES `types` WRITE;
/*!40000 ALTER TABLE `types` DISABLE KEYS */;
INSERT INTO `types` VALUES (1,'Music'),(2,'Game'),(3,'Social'),(4,'Tip'),(5,'Talk show'),(6,'Sharing'),(7,'Technical'),(8,'Entertainment'),(9,'Education');
/*!40000 ALTER TABLE `types` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) DEFAULT NULL,
  `owner_name` varchar(255) DEFAULT NULL,
  `stream_id` int(11) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `source` int(11) DEFAULT NULL,
  `video_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `owner_id` (`owner_id`),
  KEY `stream_id` (`stream_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`stream_id`) REFERENCES `stream` (`stream_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,1,'davidtuan',157,'LOL','2019-12-25 01:30:05',1,1,568),(2,1,'bdtuyen',157,'Hay','2019-12-25 01:30:35',1,1,599),(3,1,'John cena',157,'Good','2019-12-25 01:30:37',1,1,601),(4,4,'John cena',157,'So good','2019-12-25 01:30:38',1,1,602),(5,4,'John cena',157,'LOLLLL','2019-12-25 01:30:44',1,1,607),(6,4,'bdtuyen',157,'xD','2019-12-25 01:30:47',1,1,611),(7,1,'davidtuan',157,'Hi','2019-12-25 01:30:52',1,1,615),(8,1,'bdtuyen',157,'No no','2019-12-25 01:30:53',1,1,616),(9,1,'bdtuyen',157,'Don\'t do that','2019-12-25 01:30:58',1,1,622),(10,1,'John cena',157,'Take it','2019-12-25 01:31:01',1,1,625),(11,4,'John cena',157,'Nice','2019-12-25 01:31:04',1,1,627),(12,1,'bdtuyen',157,'Ok','2019-12-25 01:31:05',1,1,628),(13,1,'John cena',157,'Lolllll','2019-12-25 01:31:13',-1,1,637),(14,1,'John cena',157,'best','2019-12-25 01:31:22',1,1,646),(15,1,'bdtuyen',157,'I got it','2019-12-25 01:47:14',1,1,1597),(16,1,'bdtuyen',157,'Take it','2019-12-25 01:47:33',1,1,1617),(17,1,'John cena',157,'Alo alo','2019-12-25 01:47:50',1,1,1634),(18,1,'bdtuyen',157,'Let\'s sing a song','2019-12-25 01:48:02',1,1,1646),(19,1,'John cena',157,'Dance','2019-12-25 01:50:17',1,1,1780),(20,1,'davidtuan',159,'Nah nah','2019-12-25 02:20:40',2,1,1),(21,1,'John cena',159,'gshshdjdhdxbxhxb','2019-12-25 02:26:56',2,1,0),(22,1,'davidtuan',159,'hxhxhxxxxhxjxhx','2019-12-25 02:27:15',2,1,0),(23,1,'John cena',159,'Nah nah','2019-12-25 02:27:15',2,1,0),(24,1,'davidtuan',159,'Let\'s sing a song','2019-12-25 02:27:15',2,1,0),(25,1,'davidtuan',159,'bxhxhxjxxhxjxjx','2019-12-25 02:27:27',2,1,0),(26,1,'davidtuan',159,'hxhxhxhjxjxx','2019-12-25 02:27:30',2,1,0),(27,1,'John cena',159,'hdhxhdhddhd','2019-12-25 02:27:38',2,1,1),(28,1,'davidtuan',159,'gxhxhxhxhhx','2019-12-25 02:27:40',2,1,3),(29,1,'bdtuyen',159,'xhxhxhxhhxxzb','2019-12-25 02:27:42',2,1,5),(30,1,'davidtuan',159,'Don\'t do that','2019-12-25 02:27:42',2,1,5),(31,1,'bdtuyen',159,'hxhxhxhhx','2019-12-25 02:27:44',2,1,5),(32,1,'davidtuan',159,'hxhxhxhjxx','2019-12-25 02:27:49',2,1,5),(33,1,'bdtuyen',159,'hshzhdjzjx','2019-12-25 02:27:51',2,1,5),(34,1,'davidtuan',159,'hdhhshs','2019-12-25 02:29:55',2,1,5),(35,1,'davidtuan',159,'lol lol','2019-12-25 02:31:31',2,1,5),(36,1,'The Soul of Wind',159,'hsjshsjdhd','2019-12-25 03:47:20',2,1,5),(37,1,'The Soul of Wind',159,'hshdhshdhd','2019-12-25 03:50:19',2,1,5),(38,1,'The Soul of Wind',159,'xbdhxjxjdjd','2019-12-25 03:50:25',2,1,5),(39,1,'The Soul of Wind',159,'dhxjxjxjdjd','2019-12-25 03:50:42',2,1,1),(40,1,'The Soul of Wind',159,'duplicate','2019-12-25 03:52:18',2,1,4);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `favourite_saved`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `favourite_saved` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `stream_id` int(11) DEFAULT NULL,
  `saved_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `stream_id` (`stream_id`),
  CONSTRAINT `favourite_saved_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `favourite_saved_ibfk_2` FOREIGN KEY (`stream_id`) REFERENCES `stream` (`stream_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favourite_saved`
--

LOCK TABLES `favourite_saved` WRITE;
/*!40000 ALTER TABLE `favourite_saved` DISABLE KEYS */;
INSERT INTO `favourite_saved` VALUES (1,1,1,NULL,NULL),(2,1,7,NULL,NULL),(3,2,157,NULL,NULL),(4,2,158,NULL,NULL),(5,2,159,NULL,NULL),(6,2,1,NULL,NULL),(7,1,157,NULL,NULL),(8,1,7,NULL,NULL),(9,3,158,NULL,NULL),(10,3,159,NULL,NULL),(11,3,1,NULL,NULL);
/*!40000 ALTER TABLE `favourite_saved` ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stream_id` int(11) DEFAULT NULL,
  `message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `stream_id_fk3_idx` (`stream_id`),
  CONSTRAINT `stream_id_fk3` FOREIGN KEY (`stream_id`) REFERENCES `stream` (`stream_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `notification_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `notification_user` (
  `notify_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  KEY `notify_id` (`notify_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `notification_user_ibfk_1` FOREIGN KEY (`notify_id`) REFERENCES `notification` (`id`),
  CONSTRAINT `notification_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification_user`
--

LOCK TABLES `notification_user` WRITE;
/*!40000 ALTER TABLE `notification_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification_user` ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS `stream_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `stream_type` (
  `stream_id` int(11) NOT NULL,
  `type_id` int(11) DEFAULT NULL,
  KEY `stream_id` (`stream_id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `stream_type_ibfk_1` FOREIGN KEY (`stream_id`) REFERENCES `stream` (`stream_id`),
  CONSTRAINT `stream_type_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `types` (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stream_type`
--

LOCK TABLES `stream_type` WRITE;
/*!40000 ALTER TABLE `stream_type` DISABLE KEYS */;
INSERT INTO `stream_type` VALUES (1,1),(1,2),(1,3),(1,4),(7,5),(7,6),(7,3),(7,4),(157,7),(157,8),(157,3),(157,4),(158,1),(158,2),(158,6),(158,7),(159,5),(159,6),(159,3),(159,4),(159,7),(7,9),(157,9),(1,9);
/*!40000 ALTER TABLE `stream_type` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `subscriber`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `subscriber` (
  `subscriber_id` int(11) DEFAULT NULL,
  `publisher_id` int(11) DEFAULT NULL,
  KEY `subscriber_id` (`subscriber_id`),
  KEY `publisher_id` (`publisher_id`),
  CONSTRAINT `subscriber_ibfk_1` FOREIGN KEY (`subscriber_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `subscriber_ibfk_2` FOREIGN KEY (`publisher_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscriber`
--

LOCK TABLES `subscriber` WRITE;
/*!40000 ALTER TABLE `subscriber` DISABLE KEYS */;
INSERT INTO `subscriber` VALUES (1,1);
/*!40000 ALTER TABLE `subscriber` ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS `subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `subscription` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscription` ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS `pay_subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `pay_subscription` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subscription_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `amount` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `subscription_id` (`subscription_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `pay_subscription_ibfk_1` FOREIGN KEY (`subscription_id`) REFERENCES `subscription` (`id`),
  CONSTRAINT `pay_subscription_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_subscription`
--

LOCK TABLES `pay_subscription` WRITE;
/*!40000 ALTER TABLE `pay_subscription` DISABLE KEYS */;
/*!40000 ALTER TABLE `pay_subscription` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `user_favourite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_favourite` (
  `user_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  KEY `user_id` (`user_id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `user_favourite_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `user_favourite_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `types` (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_favourite`
--

LOCK TABLES `user_favourite` WRITE;
/*!40000 ALTER TABLE `user_favourite` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_favourite` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `ranking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
CREATE TABLE `ranking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `point` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_id_rank_idx` (`user_id`),
  CONSTRAINT `fk_user_id_rank` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
)
--
-- CREATE PROCEDURE TO CREATE DEFAULT RANKING FOR USER
--
DELIMITER //
 
CREATE PROCEDURE CreateDefaultRanking()
BEGIN
DECLARE done INT DEFAULT 0;
DECLARE user_id_temp INT;
DECLARE cur CURSOR FOR select user_id from user where user_id not in (SELECT user_id  FROM ranking where month = month(current_date()) and year = year(current_date()));
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
OPEN cur;
	REPEAT
	FETCH cur INTO user_id_temp;
		IF NOT done THEN
			INSERT into ranking(user_id, point, month, year) values (user_id_temp, 0, month(current_date()), year(current_date()));
        END IF;
    UNTIL done END REPEAT;
CLOSE cur;
END //
 
DELIMITER ;

--
-- SCHEDULE PROCEDURE
--
CREATE EVENT createDefaultRankForUser
	ON SCHEDULE EVERY 5 MINUTE
    DO
		CALL CreateDefaultRanking();

--
-- Table structure for table `user_like`
--

DROP TABLE IF EXISTS `user_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `stream_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `user_id` (`user_id`),
  KEY `stream_id` (`stream_id`),
  CONSTRAINT `user_like_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `user_like_ibfk_2` FOREIGN KEY (`stream_id`) REFERENCES `stream` (`stream_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-04 14:09:06

