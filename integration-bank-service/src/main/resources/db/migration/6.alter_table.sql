ALTER TABLE `banking_account`
    ADD FOREIGN KEY (`bank_id`) REFERENCES `bank`(`id`);

ALTER TABLE `transaction`
    ADD FOREIGN KEY (`banking_account_id`) REFERENCES `banking_account`(`id`);