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
	ON SCHEDULE EVERY 30 SECOND
    DO
		CALL CreateDefaultRanking();
