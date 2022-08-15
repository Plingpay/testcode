CREATE TABLE `banking_account` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `actual_balance` decimal(19,2) DEFAULT NULL,
   `available_balance` decimal(19,2) DEFAULT NULL,
   `account_no` varchar(255) DEFAULT NULL,
   `account_name` varchar(255) DEFAULT NULL,
   `status` varchar(255) DEFAULT NULL,
   `type` varchar(255) DEFAULT NULL,
   `type_account_no` int DEFAULT NULL COMMENT '0: Bank account number, 1: Bank card number',
   `bank_id` bigint DEFAULT NULL,
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;