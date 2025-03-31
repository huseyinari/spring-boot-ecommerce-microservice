DROP TABLE IF EXISTS inventory;
DROP SEQUENCE IF EXISTS inventory_id_sequence;

CREATE SEQUENCE inventory_id_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 2147483647
START 100000
NO CYCLE;

CREATE TABLE inventory (
    id BIGINT,
    sku_code VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 0,
    --
    created_by VARCHAR(100) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP
);

ALTER TABLE inventory ADD CONSTRAINT pk_inventory_id PRIMARY KEY (id);
ALTER TABLE inventory ADD CONSTRAINT un_inventory_sku_code UNIQUE (sku_code);