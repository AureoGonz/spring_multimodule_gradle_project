DELETE FROM stock;
DELETE from warehouse;
ALTER SEQUENCE warehouse_id_seq RESTART WITH 1;
ALTER SEQUENCE stock_id_seq RESTART WITH 1;
INSERT INTO warehouse (id, name) VALUES (-1,'shoes');
INSERT INTO warehouse (id, name) VALUES (0,'books');
INSERT INTO warehouse (id, name) VALUES (-2,'fallos');

INSERT INTO stock (id, product, quantity, warehouse, deleted_at)
VALUES (-3, -1, 12, -1, NULL),
(-2, -2, 11, 0, NULL),
(-1, -1, 14, -1, NULL),
(0, 0, 10, 0, NULL),
(-4, 4, 10, -2, NULL);
