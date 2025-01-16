DELETE
FROM shop;
DELETE
FROM owner;
INSERT INTO owner (id, shop_id)
VALUES (1, NULL),
       (2, NULL);