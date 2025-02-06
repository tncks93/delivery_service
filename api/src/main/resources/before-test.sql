SET foreign_key_checks = 0;
TRUNCATE TABLE owner;
TRUNCATE TABLE shop;
TRUNCATE TABLE login_user;
SET foreign_key_checks = 1;

INSERT INTO owner (shop_id)
VALUES (NULL),
       (NULL);