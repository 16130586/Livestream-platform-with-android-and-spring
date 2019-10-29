create user if not EXISTS 'maxwell'@'%' IDENTIFIED BY 'maxwell';
ALTER USER 'maxwell'@'%' IDENTIFIED WITH mysql_native_password BY 'maxwell';
GRANT ALL on *.* to 'maxwell'@'%';
GRANT SELECT, REPLICATION CLIENT, REPLICATION SLAVE on *.* to 'maxwell'@'%';
flush privileges;

CREATE SCHEMA IF NOT EXISTS `livestream`;
USE `livestream`;

CREATE TABLE IF NOT EXISTS `User` (
  `user_id` integer,
  `username` varchar(255),
  `password` varchar(255),
  `nick_name` varchar(255),
  `avatar` blob,
  `gmail` varchar(255),
  `forgot_token` varchar(255),
  `subcribe_total` integer
);

CREATE TABLE IF NOT EXISTS `subscriber` (
  `subcriber_id` integer,
  `publisher_id` integer
);

CREATE TABLE IF NOT EXISTS `types` (
  `type_id` integer,
  `type_name` varchar(255)
);

CREATE TABLE IF NOT EXISTS `stream` (
  `stream_id` integer,
  `wowza_id` varchar(255),
  `owner_id` integer,
  `total_view` integer,
  `start_time` datetime,
  `end_time` datetime,
  `stream_status` integer,
  `forwards` text,
  `forwards_url` text,
  `stored_url` text
);

CREATE TABLE IF NOT EXISTS `stream_type` (
  `stream_id` integer,
  `type_id` integer
);

CREATE TABLE IF NOT EXISTS `user_favorite` (
  `user_id` integer,
  `type_id` integer
);

CREATE TABLE IF NOT EXISTS `notification` (
  `id` integer,
  `stream_id` integer,
  `message` varchar(255),
  `status` integer,
  `created_time` datetime
);

CREATE TABLE IF NOT EXISTS `notification_user` (
  `notify_id` integer,
  `user_id` integer
);

CREATE TABLE IF NOT EXISTS `comment` (
  `comment_id` integer,
  `owner_id` integer,
  `stream_id` integer,
  `message` varchar(255),
  `created_time` datetime,
  `status` integer,
  `source` integer
);

CREATE TABLE IF NOT EXISTS `favorite_saved` (
  `user_id` integer,
  `stream_id` integer,
  `saved_time` datetime,
  `status` integer
);

CREATE TABLE IF NOT EXISTS `subscription` (
  `id` integer,
  `name` varchar(255),
  `type` integer,
  `unit_price` double
);

CREATE TABLE IF NOT EXISTS `pay_subscription` (
  `subscription_id` integer,
  `user_id` integer,
  `start_time` datetime,
  `end_time` datetime,
  `amount` double
);
