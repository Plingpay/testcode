CREATE TABLE `bank` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `bank_no` varchar(20) DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    `code` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;