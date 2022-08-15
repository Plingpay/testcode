CREATE TABLE `transaction` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `transaction_request_id` varchar(255) NOT NULL,
   `code` varchar(255) DEFAULT NULL COMMENT 'trans_id',
   `request_amount` decimal(19,2) DEFAULT NULL,
   `transfer_amount` decimal(19,2) DEFAULT NULL,
   `content` varchar(255) DEFAULT NULL,
   `fee` decimal(19,2) DEFAULT NULL,
   `type` varchar(255) DEFAULT NULL COMMENT 'TRANSFER_BANK: Pay-out, REFUND: Refund ,DEPOSIT: Deposit, WITHDRAW: Withdraw',
   `status` varchar(255) DEFAULT NULL,
   `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `banking_account_id` bigint DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;