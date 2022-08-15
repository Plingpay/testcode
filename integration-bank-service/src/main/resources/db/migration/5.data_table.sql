-- bank table
INSERT INTO integration_bank.bank
    (id, bank_no, name, code)
VALUES (1, ''B4Z4QP3V'', ''AGRIBANK - NH Nong Nghiep va PTNT VN'', ''AGRIBANK'');
INSERT INTO integration_bank.bank
    (id, bank_no, name, code)
VALUES (2, ''DKZYENER'', ''VIETCOMBANK - NH Ngoai Thuong Viet Nam'', ''VIETCOMBANK'');
INSERT INTO integration_bank.bank
    (id, bank_no, name, code)
VALUES (3, ''WBP84NRJ'', ''VIETINBANK - NH Cong Thuong VN'', ''VIETINBANK'');
INSERT INTO integration_bank.bank
    (id, bank_no, name, code)
VALUES (4, ''BKZ6LBNJ'', ''VRB - NH Lien Doanh Viet Nga'', ''VRB'');
INSERT INTO integration_bank.bank
    (id, bank_no, name, code)
VALUES (5, ''KBZG80ZX'', ''VRB - NH Lien Doanh Viet Nga'', ''TPBANK'');

-- transaction table
INSERT INTO integration_bank.banking_account
(id, actual_balance, available_balance, account_no, account_name, status, `type`, type_account_no, bank_id)
VALUES (1, 100000.00, 100000.00, '1023020330000', ''NGUYEN VAN A'', ''ACTIVE'', ''SAVING_ACCOUNT'', 0, 1);
INSERT INTO integration_bank.banking_account
(id, actual_balance, available_balance, account_no, account_name, status, `type`, type_account_no, bank_id)
VALUES (2, 200000.00, 200000.00, '1023020330001', ''NGUYEN VAN B'', ''ACTIVE'', ''SAVING_ACCOUNT'', 0, 2);

-- user table
INSERT INTO integration_bank.`user`
    (id, email, first_name, identification_number, last_name)
VALUES (1, ''a@gmail.com'', ''A'', ''808829932V'', ''NGUYEN VAN'');
INSERT INTO integration_bank.`user`
    (id, email, first_name, identification_number, last_name)
VALUES (2, ''b@gmail.com'', '' B'', ''901830556V'', ''NGUYEN VAN'');