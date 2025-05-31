DROP TABLE IF EXISTS product_image;
DROP TABLE IF EXISTS products;
DROP SEQUENCE IF EXISTS product_image_id_sequence;

CREATE TABLE products (
    id VARCHAR(100),
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    sku_code VARCHAR(255) NOT NULL,
    price DEC(10, 2) NOT NULL,
    category_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    failure_description VARCHAR(4000),
    user_id VARCHAR(255) NOT NULL,
    --
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP
);

ALTER TABLE products ADD CONSTRAINT pk_products_id PRIMARY KEY (id);
ALTER TABLE products ADD CONSTRAINT un_products_name UNIQUE (name);
ALTER TABLE products ADD CONSTRAINT un_products_sku_code UNIQUE (sku_code);


CREATE SEQUENCE product_image_id_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 2147483647
START 100000
NO CYCLE;

CREATE TABLE product_image (
    id BIGINT,
    product_id VARCHAR(100) NOT NULL,
    storage_object_id BIGINT NOT NULL,
    --
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP
);

ALTER TABLE product_image ADD CONSTRAINT pk_product_image_id PRIMARY KEY (id);
ALTER TABLE product_image ADD CONSTRAINT fk_product_image_product_id FOREIGN KEY (product_id) REFERENCES products(id);

CREATE UNIQUE INDEX un_ind_product_image_product_id_storage_object_id ON product_image (product_id, storage_object_id);