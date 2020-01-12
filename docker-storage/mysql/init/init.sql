create user if not EXISTS 'maxwell'@'%' IDENTIFIED BY 'maxwell';
ALTER USER 'maxwell'@'%' IDENTIFIED WITH mysql_native_password BY 'maxwell';
GRANT ALL on *.* to 'maxwell'@'%';
GRANT SELECT, REPLICATION CLIENT, REPLICATION SLAVE on *.* to 'maxwell'@'%';
flush privileges;

CREATE SCHEMA IF NOT EXISTS `livestream`;
USE `livestream`;

-- drop table
DROP TABLE IF EXISTS `notification_user`;
DROP TABLE IF EXISTS `subscriber`;
DROP TABLE IF EXISTS `notification`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `stream_type`;
DROP TABLE IF EXISTS `user_favourite`;
DROP TABLE IF EXISTS `types`;
DROP TABLE IF EXISTS `favourite_saved`;
DROP TABLE IF EXISTS `pay_subscription`;
DROP TABLE IF EXISTS `subscription`;
DROP TABLE IF EXISTS `user_like`;
DROP TABLE IF EXISTS `ranking`;
DROP TABLE IF EXISTS `report`;
DROP TABLE IF EXISTS `stream`;
DROP TABLE IF EXISTS `user`;
-- end drop

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
  `is_publisher` int(11) DEFAULT 1,
  `is_activated` int(11) DEFAULT 1,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



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
  `is_flagged` int(11) DEFAULT -1,
  PRIMARY KEY (`stream_id`),
  KEY `owner_id` (`owner_id`),
  CONSTRAINT `stream_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


 SET character_set_client = utf8mb4 ;
CREATE TABLE `types` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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




CREATE TABLE `notification_user` (
  `notify_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  KEY `notify_id` (`notify_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `notification_user_ibfk_1` FOREIGN KEY (`notify_id`) REFERENCES `notification` (`id`),
  CONSTRAINT `notification_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;






CREATE TABLE `stream_type` (
  `stream_id` int(11) NOT NULL,
  `type_id` int(11) DEFAULT NULL,
  KEY `stream_id` (`stream_id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `stream_type_ibfk_1` FOREIGN KEY (`stream_id`) REFERENCES `stream` (`stream_id`),
  CONSTRAINT `stream_type_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `types` (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;






CREATE TABLE `subscriber` (
  `subscriber_id` int(11) DEFAULT NULL,
  `publisher_id` int(11) DEFAULT NULL,
  KEY `subscriber_id` (`subscriber_id`),
  KEY `publisher_id` (`publisher_id`),
  CONSTRAINT `subscriber_ibfk_1` FOREIGN KEY (`subscriber_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `subscriber_ibfk_2` FOREIGN KEY (`publisher_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `subscription` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;





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



 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_favourite` (
  `user_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  KEY `user_id` (`user_id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `user_favourite_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `user_favourite_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `types` (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




CREATE TABLE `ranking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `point` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_id_rank_idx` (`user_id`),
  CONSTRAINT `fk_user_id_rank` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `stream_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `user_id` (`user_id`),
  KEY `stream_id` (`stream_id`),
  CONSTRAINT `user_like_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `user_like_ibfk_2` FOREIGN KEY (`stream_id`) REFERENCES `stream` (`stream_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `report` (
  `report_id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) DEFAULT NULL,
  `live_id` int(11) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`report_id`),
  UNIQUE KEY `id_UNIQUE` (`report_id`),
  KEY `owner_id` (`owner_id`),
  KEY `live_id` (`live_id`),
  CONSTRAINT `report_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `report_stream_id` FOREIGN KEY (`live_id`) REFERENCES `stream` (`stream_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



