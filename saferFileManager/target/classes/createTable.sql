

CREATE TABLE `users` (
    `id` INT(10) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL ,
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
    `filename` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`)
) 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci 
ENGINE=InnoDB;
