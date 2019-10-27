create user if not EXISTS 'maxwell'@'%' IDENTIFIED BY 'maxwell';
ALTER USER 'maxwell'@'%' IDENTIFIED WITH mysql_native_password BY 'maxwell';
GRANT ALL on *.* to 'maxwell'@'%';
GRANT SELECT, REPLICATION CLIENT, REPLICATION SLAVE on *.* to 'maxwell'@'%';
flush privileges;