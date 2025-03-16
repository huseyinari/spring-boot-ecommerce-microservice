DROP TABLE IF EXISTS orders;
DROP SEQUENCE IF EXISTS orders_id_sequence;

CREATE SEQUENCE orders_id_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 2147483647
START 100000
NO CYCLE;

CREATE TABLE orders (
    id BIGINT,
    order_number VARCHAR(255) NOT NULL,
    sku_code VARCHAR(255) NOT NULL,
    price DEC(10, 2) NOT NULL,
    quantity INTEGER NOT NULL,
    --
    created_by VARCHAR(100) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP
);

ALTER TABLE orders ADD CONSTRAINT pk_orders_id PRIMARY KEY (id);