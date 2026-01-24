DROP SEQUENCE IF EXISTS product_image_id_sequence;
DROP SEQUENCE IF EXISTS product_attribute_value_id_sequence;
DROP SEQUENCE IF EXISTS product_attribute_id_sequence;
DROP SEQUENCE IF EXISTS product_variant_index_id_sequence;
DROP SEQUENCE IF EXISTS product_variant_value_id_sequence;
DROP SEQUENCE IF EXISTS product_variant_option_id_sequence;
DROP SEQUENCE IF EXISTS product_variant_id_sequence;
DROP SEQUENCE IF EXISTS product_inspects_id_sequence;

DROP TABLE IF EXISTS product_image;
DROP TABLE IF EXISTS product_attribute_value;
DROP TABLE IF EXISTS product_attribute;
DROP TABLE IF EXISTS product_variant_index;
DROP TABLE IF EXISTS product_variant_value;
DROP TABLE IF EXISTS product_variant_option;
DROP TABLE IF EXISTS product_variant;
DROP TABLE IF EXISTS product_inspect;
DROP TABLE IF EXISTS products;

CREATE TABLE products (
    id VARCHAR(100),
    name VARCHAR(100) NOT NULL,
    description VARCHAR(9999),
    sku_code VARCHAR(255) NOT NULL,
    price DEC(10, 2) NOT NULL,
    discount DEC(10, 2),
    discounted_price DEC(10, 2) NOT NULL,
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

CREATE SEQUENCE product_inspects_id_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 2147483647
START 100000
NO CYCLE;

CREATE TABLE product_inspect (
    id BIGINT,
    inspected_by_ip_address VARCHAR(50) NOT NULL,
    product_id VARCHAR(100) NOT NULL,
    --
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP
);

ALTER TABLE product_inspect ADD CONSTRAINT pk_product_inspect_id PRIMARY KEY (id);
ALTER TABLE product_inspect ADD CONSTRAINT fk_product_inspect_product_id FOREIGN KEY (product_id) REFERENCES products(id);

CREATE SEQUENCE product_attribute_id_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 2147483647
START 100000
NO CYCLE;

CREATE TABLE product_attribute (
    id BIGINT,
    name VARCHAR(100) NOT NULL,
    query_name VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    --
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP
);

ALTER TABLE product_attribute ADD CONSTRAINT pk_product_attribute_id PRIMARY KEY (id);
ALTER TABLE product_attribute ADD CONSTRAINT un_product_attribute_query_name UNIQUE (query_name);

CREATE SEQUENCE product_attribute_value_id_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 2147483647
START 100000
NO CYCLE;

CREATE TABLE product_attribute_value (
    id BIGINT,
    product_id VARCHAR(100) NOT NULL,
    product_attribute_id BIGINT NOT NULL,
    attribute_value VARCHAR(255) NOT NULL,
    --
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP
);

ALTER TABLE product_attribute_value ADD CONSTRAINT pk_product_attribute_value_id PRIMARY KEY (id);
ALTER TABLE product_attribute_value ADD CONSTRAINT fk_product_attribute_value_product_id FOREIGN KEY (product_id) REFERENCES products(id);
ALTER TABLE product_attribute_value ADD CONSTRAINT fk_product_attribute_value_product_attribute_id FOREIGN KEY (product_attribute_id) REFERENCES product_attribute(id);
CREATE UNIQUE INDEX un_ind_product_attribute_value_product_id_product_attribute_id_attribute_value ON product_attribute_value (product_id, product_attribute_id, attribute_value);

CREATE SEQUENCE product_variant_id_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 2147483647
START 100000
NO CYCLE;

CREATE TABLE product_variant (
    id BIGINT,
    name VARCHAR(100) NOT NULL,
    query_name VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    data_type VARCHAR(50) NOT NULL,
    ui_component VARCHAR(50) NOT NULL,
    min_value VARCHAR(100),
    max_value VARCHAR(100),
    product_variant_index_json_order_number INTEGER NOT NULL,
    --
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP
);

ALTER TABLE product_variant ADD CONSTRAINT pk_product_variant_id PRIMARY KEY (id);
ALTER TABLE product_variant ADD CONSTRAINT un_product_variant_query_name UNIQUE (query_name);
CREATE UNIQUE INDEX un_ind_product_variant_product_variant_index_json_order_number ON product_variant (product_variant_index_json_order_number);

CREATE SEQUENCE product_variant_option_id_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 2147483647
START 100000
NO CYCLE;

CREATE TABLE product_variant_option (
    id BIGINT,
    product_variant_id BIGINT NOT NULL,
    option_value VARCHAR(255) NOT NULL,
    --
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP
);

ALTER TABLE product_variant_option ADD CONSTRAINT pk_product_variant_option_id PRIMARY KEY (id);
ALTER TABLE product_variant_option ADD CONSTRAINT fk_product_variant_option_product_variant_id FOREIGN KEY (product_variant_id) REFERENCES product_variant(id);
CREATE UNIQUE INDEX un_ind_product_variant_option_product_variant_id_option_value ON product_variant_option (product_variant_id, option_value);

CREATE SEQUENCE product_variant_value_id_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 2147483647
START 100000
NO CYCLE;

CREATE TABLE product_variant_value (
    id BIGINT,
    product_id VARCHAR(100) NOT NULL,
    product_variant_id BIGINT NOT NULL,
    variant_value VARCHAR(255) NOT NULL,
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP
);

ALTER TABLE product_variant_value ADD CONSTRAINT pk_product_variant_value_id PRIMARY KEY (id);
ALTER TABLE product_variant_value ADD CONSTRAINT fk_product_variant_value_product_id FOREIGN KEY (product_id) REFERENCES products(id);
ALTER TABLE product_variant_value ADD CONSTRAINT fk_product_variant_value_product_variant_id FOREIGN KEY (product_variant_id) REFERENCES product_variant(id);
CREATE UNIQUE INDEX un_ind_product_variant_value_product_id_product_variant_id_variant_value ON product_variant_value (product_id, product_variant_id, variant_value);

CREATE SEQUENCE product_variant_index_id_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 2147483647
START 100000
NO CYCLE;

CREATE TABLE product_variant_index (
    id BIGINT,
    product_id VARCHAR(100) NOT NULL,
    variant_value_index JSONB NOT NULL,
    sku_code VARCHAR(255) NOT NULL,
    stock INTEGER NOT NULL,
    price DEC(10, 2) NOT NULL,
    discount DEC(10, 2),
    discounted_price DEC(10, 2) NOT NULL,
    query_order INTEGER NOT NULL,
    --
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP
);
ALTER TABLE product_variant_index ADD CONSTRAINT pk_product_variant_index_id PRIMARY KEY (id);
ALTER TABLE product_variant_index ADD CONSTRAINT fk_product_variant_index_product_id FOREIGN KEY (product_id) REFERENCES products(id);
ALTER TABLE product_variant_index ADD CONSTRAINT un_product_variant_index_sku_code UNIQUE (sku_code);
ALTER TABLE product_variant_index ADD CONSTRAINT un_product_variant_index_query_order_product_id UNIQUE (query_order, product_id);

CREATE SEQUENCE product_image_id_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 2147483647
START 100000
NO CYCLE;

CREATE TABLE product_image (
    id BIGINT,
    product_id VARCHAR(100) NOT NULL,
    product_variant_index_id BIGINT,
    storage_object_id BIGINT NOT NULL,
    --
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP
);

ALTER TABLE product_image ADD CONSTRAINT pk_product_image_id PRIMARY KEY (id);
ALTER TABLE product_image ADD CONSTRAINT fk_product_image_product_id FOREIGN KEY (product_id) REFERENCES products(id);
ALTER TABLE product_image ADD CONSTRAINT fk_product_image_product_variant_index_id FOREIGN KEY (product_variant_index_id) REFERENCES product_variant_index(id);
CREATE UNIQUE INDEX un_ind_product_image_product_id_product_variant_index_id_storage_object_id ON product_image (product_id, product_variant_index_id, storage_object_id);