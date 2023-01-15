CREATE TABLE `Member`(
                         `member_idx` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                         `nickname` VARCHAR(255) NOT NULL,
                         `profile_email` VARCHAR(255) NOT NULL,
                         `social_email` VARCHAR(255) NOT NULL,
                         `uni_email` VARCHAR(255) NOT NULL,
                         `content` VARCHAR(255) NOT NULL,
                         `profile_url` TEXT NOT NULL,
                         `created_at` DATETIME NOT NULL,
                         `updated_at` DATETIME NOT NULL,
                         `status` VARCHAR(255) NOT NULL,
                         primary key (member_idx)
);
CREATE TABLE `Career`(
                         `career_idx` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                         `company` VARCHAR(255) NOT NULL,
                         `position` VARCHAR(255) NOT NULL,
                         `start_date` DATE NOT NULL,
                         `end_date` DATE NULL,
                         `is_working` VARCHAR(255) NOT NULL,
                         `member_idx` BIGINT UNSIGNED NOT NULL,
                         primary key (career_idx)
);
CREATE TABLE `Education`(
                            `education_idx` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                            `institution` VARCHAR(255) NOT NULL,
                            `major` VARCHAR(255) NOT NULL,
                            `is_learning` VARCHAR(255) NOT NULL,
                            `member_idx` BIGINT UNSIGNED NOT NULL,
                            `start_date` DATE NOT NULL,
                            `end_date` DATE NOT NULL,
                            primary key (education_idx)
);
CREATE TABLE `SNS`(
                      `sns_idx` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                      `address` VARCHAR(255) NOT NULL,
                      `member_idx` BIGINT UNSIGNED NOT NULL,
                      primary key (sns_idx)
);
CREATE TABLE `Seminar`(
                          `seminar_idx` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                          `title` VARCHAR(255) NOT NULL,
                          `date` DATETIME NOT NULL,
                          `location` VARCHAR(255) NOT NULL,
                          `fee` INT NOT NULL,
                          `end_date` DATETIME NOT NULL,
                          `status` VARCHAR(255) NOT NULL,
                          primary key (seminar_idx)
);
CREATE TABLE `Presentation`(
                               `presentation_idx` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                               `title` VARCHAR(255) NOT NULL,
                               `nickname` VARCHAR(255) NOT NULL,
                               `organization` BIGINT NOT NULL,
                               `content` TEXT NOT NULL,
                               `presentation_url` TEXT NOT NULL,
                               `seminar_idx` BIGINT UNSIGNED NOT NULL,
                               primary key (presentation_idx)
);
CREATE TABLE `MemberSeminar`(
                                `member_seminar_idx` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                `member_idx` BIGINT UNSIGNED NOT NULL,
                                `seminar_idx` BIGINT UNSIGNED NOT NULL,
                                `status` VARCHAR(255) NOT NULL,
                                `bank` VARCHAR(255) NULL,
                                `account` VARCHAR(255) NULL,
                                primary key (member_seminar_idx)
);
CREATE TABLE `MemberNetworking`(
                                   `member_networking_idx` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                   `member_idx` BIGINT UNSIGNED NOT NULL,
                                   `networking_idx` BIGINT UNSIGNED NOT NULL,
                                   `status` VARCHAR(255) NOT NULL,
                                   primary key (member_networking_idx)
);
CREATE TABLE `Networking`(
                             `networking_idx` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                             `title` VARCHAR(255) NOT NULL,
                             `date` DATETIME NOT NULL,
                             `location` VARCHAR(255) NOT NULL,
                             `fee` INT NOT NULL,
                             `end_date` DATETIME NOT NULL,
                             `status` VARCHAR(255) NOT NULL,
                             primary key (networking_idx)
);
CREATE TABLE `QnA`(
                      `qna_idx` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                      `member_idx` BIGINT UNSIGNED NOT NULL,
                      `email` VARCHAR(255) NOT NULL,
                      `category` VARCHAR(255) NOT NULL,
                      `content` TEXT NOT NULL,
                      primary key (qna_idx)
);
CREATE TABLE `MemberQuit`(
                             `member_quit_idx` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                             `member_idx` BIGINT UNSIGNED NOT NULL,
                             `content` TEXT NOT NULL,
                             `category` VARCHAR(255) NOT NULL,
                             primary key (member_quit_idx)
);
ALTER TABLE
    `MemberQuit` ADD CONSTRAINT `memberquit_member_idx_foreign` FOREIGN KEY(`member_idx`) REFERENCES `Member`(`member_idx`);
ALTER TABLE
    `MemberSeminar` ADD CONSTRAINT `memberseminar_member_idx_foreign` FOREIGN KEY(`member_idx`) REFERENCES `Member`(`member_idx`);
ALTER TABLE
    `MemberSeminar` ADD CONSTRAINT `memberseminar_seminar_idx_foreign` FOREIGN KEY(`seminar_idx`) REFERENCES `Seminar`(`seminar_idx`);
ALTER TABLE
    `MemberNetworking` ADD CONSTRAINT `membernetworking_member_idx_foreign` FOREIGN KEY(`member_idx`) REFERENCES `Member`(`member_idx`);
ALTER TABLE
    `MemberNetworking` ADD CONSTRAINT `membernetworking_networking_idx_foreign` FOREIGN KEY(`networking_idx`) REFERENCES `Networking`(`networking_idx`);
ALTER TABLE
    `QnA` ADD CONSTRAINT `qna_member_idx_foreign` FOREIGN KEY(`member_idx`) REFERENCES `Member`(`member_idx`);
ALTER TABLE
    `Presentation` ADD CONSTRAINT `presentation_seminar_idx_foreign` FOREIGN KEY(`seminar_idx`) REFERENCES `Seminar`(`seminar_idx`);
ALTER TABLE
    `SNS` ADD CONSTRAINT `sns_member_idx_foreign` FOREIGN KEY(`member_idx`) REFERENCES `Member`(`member_idx`);
ALTER TABLE
    `Career` ADD CONSTRAINT `career_member_idx_foreign` FOREIGN KEY(`member_idx`) REFERENCES `Member`(`member_idx`);
ALTER TABLE
    `Education` ADD CONSTRAINT `education_member_idx_foreign` FOREIGN KEY(`member_idx`) REFERENCES `Member`(`member_idx`);