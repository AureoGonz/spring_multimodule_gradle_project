
DELETE FROM product;
DELETE FROM category;
ALTER SEQUENCE product_id_seq RESTART WITH 1;
ALTER SEQUENCE category_id_seq RESTART WITH 1;
INSERT INTO category (id, name) VALUES (1,'shoes');
INSERT INTO category (id, name) VALUES (2,'books');
INSERT INTO category (id, name) VALUES (3,'electronics');

INSERT INTO product (id, name, description, price, deleted_at, category)
VALUES (-2, 'adidas Cloudfoam Ultimate','Walk in the air in the black / black CLOUDFOAM ULTIMATE running shoe from ADIDAS',178.89,NULL,1);

INSERT INTO product (id, name, description, price, deleted_at, category)
VALUES (-1, 'under armour Men ''s Micro G Assert � 7','under armour Men ''Lightweight mesh upper delivers complete breathability . Durable leather overlays for stability ',NULL,12.51);


INSERT INTO product (id, name, description, price, deleted_at, category)
VALUES (0, 'Spring Boot in Action','under armour Men '' Craig Walls is a software developer at Pivotal and is the author of Spring in Action',40.06,NULL,2);