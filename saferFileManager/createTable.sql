

CREATE TABLE `users` (
    `id` INT(10) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `role` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255),
    PRIMARY KEY (`id`)
) 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci 
ENGINE=InnoDB;


CREATE TABLE `shares` (
    `id` INT(10) NOT NULL AUTO_INCREMENT,
    `createtime` DATE NOT NULLusers,
    `user_id` INT NOT NULL,
    `data` VARBINARY(1024*1024) NULL,
    PRIMARY KEY (`id`)
) 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci 
ENGINE=InnoDB;


DROP EVENT IF EXISTS clean_expired_shares;

SET GLOBAL event_scheduler = ON;

CREATE EVENT IF NOT EXISTS clean_expired_shares
ON SCHEDULE EVERY 60*60 SECOND
DO
DELETE FROM shares
WHERE CAST(endTime AS UNSIGNED) < UNIX_TIMESTAMP(NOW()) * 1000;

