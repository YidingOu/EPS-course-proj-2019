-- MySQL Script generated by MySQL Workbench
-- Thu Apr  4 17:13:43 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema pivdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `pivdb` ;

-- -----------------------------------------------------
-- Schema pivdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `pivdb` DEFAULT CHARACTER SET utf8 ;
USE `pivdb` ;

-- -----------------------------------------------------
-- Table `pivdb`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pivdb`.`user` ;

CREATE TABLE IF NOT EXISTS `pivdb`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `displayName` VARCHAR(45) NULL,
  `role` INT NOT NULL,
  `pass` VARCHAR(45) NOT NULL,
  `salt` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `Name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pivdb`.`email`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pivdb`.`email` ;

CREATE TABLE IF NOT EXISTS `pivdb`.`email` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(80) NULL,
  `date` DATETIME NOT NULL,
  `code` VARCHAR(45) NOT NULL,
  `userId` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `Id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `Email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_Email_User_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `fk_Email_User`
    FOREIGN KEY (`userId`)
    REFERENCES `pivdb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pivdb`.`post`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pivdb`.`post` ;

CREATE TABLE IF NOT EXISTS `pivdb`.`post` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` INT NOT NULL,
  `updated` TINYINT(10) NOT NULL,
  `key` VARCHAR(20) NULL,
  `userId` INT NOT NULL,
  `staffId` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `Id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_Post_User1_idx` (`userId` ASC) VISIBLE,
  INDEX `fk_Post_User2_idx` (`staffId` ASC) VISIBLE,
  CONSTRAINT `fk_Post_User1`
    FOREIGN KEY (`userId`)
    REFERENCES `pivdb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Post_User2`
    FOREIGN KEY (`staffId`)
    REFERENCES `pivdb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pivdb`.`audit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pivdb`.`audit` ;

CREATE TABLE IF NOT EXISTS `pivdb`.`audit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `action` VARCHAR(50) NOT NULL,
  `userId` INT NOT NULL,
  `postId` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `Id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_Audit_User1_idx` (`userId` ASC) VISIBLE,
  INDEX `fk_Audit_Post1_idx` (`postId` ASC) VISIBLE,
  CONSTRAINT `fk_Audit_User1`
    FOREIGN KEY (`userId`)
    REFERENCES `pivdb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Audit_Post1`
    FOREIGN KEY (`postId`)
    REFERENCES `pivdb`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pivdb`.`conversation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pivdb`.`conversation` ;

CREATE TABLE IF NOT EXISTS `pivdb`.`conversation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATETIME NOT NULL,
  `data` TEXT NOT NULL,
  `postId` INT NOT NULL,
  `reply` TINYINT NOT NULL,
  `userId` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `Id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_Conversation_Post1_idx` (`postId` ASC) VISIBLE,
  INDEX `fk_Conversation_User1_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `fk_Conversation_Post1`
    FOREIGN KEY (`postId`)
    REFERENCES `pivdb`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Conversation_User1`
    FOREIGN KEY (`userId`)
    REFERENCES `pivdb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pivdb`.`contact`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pivdb`.`contact` ;

CREATE TABLE IF NOT EXISTS `pivdb`.`contact` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `address` TEXT NULL,
  `number` VARCHAR(45) NULL,
  `postId` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `Id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_Contact_Post1_idx` (`postId` ASC) VISIBLE,
  CONSTRAINT `fk_Contact_Post1`
    FOREIGN KEY (`postId`)
    REFERENCES `pivdb`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
