/* products */
INSERT INTO public.products
(id, "name", description, sku_code, price, discount, discounted_price, category_id, status, user_id, created_date, created_by)
VALUES('7db7596b-306c-4ab9-a623-c835e421d533', 'Iphone 14', 'Iphone 14 Orjinal Türkiye Garantili', 'iphone_14', 50000.00, 0, 50000, 100011, 'SUCCESS', 'c043d233-6a49-4fd8-96fe-5f8bc96c0bddc043d233-6a49-4fd8-96fe-5f8bc96c0bdd', '2025-07-26T00:00:00', 'system');

INSERT INTO public.products
(id, "name", description, sku_code, price, discount, discounted_price, category_id, status, user_id, created_date, created_by)
VALUES('a2b7596b-306c-4ab9-a623-c835e421d533', 'Macbook Air 2025', 'Macbook Air 2025 Orjinal Türkiye Garantili 16gb RAM 256gb SSD M3 Chip', 'macbook_air_2025', 65000, 500, 64500, 100009, 'SUCCESS', 'c043d233-6a49-4fd8-96fe-5f8bc96c0bddc043d233-6a49-4fd8-96fe-5f8bc96c0bdd', '2025-07-26T01:00:00', 'system');

INSERT INTO public.products
(id, "name", description, sku_code, price, discount, discounted_price, category_id, status, user_id, created_date, created_by)
VALUES('b4b7596b-306c-4ab9-a623-c835e421d533', 'Nike Spor Ayakkabı', 'Nike spor ayakkabı', 'nike_spor_ayakkabi_11', 4500, 0, 4500, 100005, 'SUCCESS', 'c043d233-6a49-4fd8-96fe-5f8bc96c0bddc043d233-6a49-4fd8-96fe-5f8bc96c0bdd', '2025-07-26T02:00:00', 'system');

INSERT INTO public.products
(id, "name", description, sku_code, price, discount, discounted_price, category_id, status, user_id, created_date, created_by)
VALUES('c6b7596b-306c-4ab9-a623-c835e421d533', 'Sagopa Kajmer Bir Pesimistin Gözyaşları Albüm', 'sago albüm', 'sagopa_kajmer_bpm_album', 2000, 0, 2000, 100008, 'SUCCESS', 'c043d233-6a49-4fd8-96fe-5f8bc96c0bddc043d233-6a49-4fd8-96fe-5f8bc96c0bdd', '2025-07-26T03:00:00', 'system');

INSERT INTO public.products
(id, "name", description, sku_code, price, discount, discounted_price, category_id, status, user_id, created_date, created_by)
VALUES('u7b7596b-306c-4ab9-a623-c835e421d533', 'Seiko SRPD63K1 Erkek Kol Saati', 'Kol Saati', 'seiko_srpd63k1', 16079.70, 0, 16079.70, 100001, 'SUCCESS', 'c043d233-6a49-4fd8-96fe-5f8bc96c0bddc043d233-6a49-4fd8-96fe-5f8bc96c0bdd', '2025-07-26T04:00:00', 'system');

INSERT INTO public.products
(id, "name", description, sku_code, price, discount, discounted_price, category_id, status, user_id, created_date, created_by)
VALUES('p9b7596b-306c-4ab9-a623-c835e421d533', 'Sinangil Çok Amaçlı Un 1kg', 'sinangil un', 'sinangil_un_1kg', 132, 0, 132, 100001, 'SUCCESS', 'c043d233-6a49-4fd8-96fe-5f8bc96c0bddc043d233-6a49-4fd8-96fe-5f8bc96c0bdd', '2025-07-26T05:00:00', 'system');

INSERT INTO public.products
(id, "name", description, sku_code, price, discount, discounted_price, category_id, status, user_id, created_date, created_by)
VALUES('q2b7596b-306c-4ab9-a623-c835e421d533', 'Samsung Galaxy Tab A9', 'tablet', 'samsung_galaxy_tab_a9', 4319, 0, 4319, 100009, 'SUCCESS', 'c043d233-6a49-4fd8-96fe-5f8bc96c0bddc043d233-6a49-4fd8-96fe-5f8bc96c0bdd', '2025-07-26T06:00:00', 'system');

INSERT INTO public.products
(id, "name", description, sku_code, price, discount, discounted_price, category_id, status, user_id, created_date, created_by)
VALUES('t2b7596b-306c-4ab9-a623-c835e421d533', 'Lenovo 400 Wireless Kablosuz Mouse Siyah GY50R91293', 'mouse', 'lenovo_400_wireless_kablosuz_siyah_mouse_gy50r91293', 299, 100, 399, 100009, 'SUCCESS', 'c043d233-6a49-4fd8-96fe-5f8bc96c0bddc043d233-6a49-4fd8-96fe-5f8bc96c0bdd', '2025-07-26T07:00:00', 'system');

/* product images */
INSERT INTO public.product_image
(id, product_id, storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('product_image_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', 100010, 'huseyinari', '2025-07-27 02:04:48.056', 'huseyinari', '2025-07-27 02:04:48.056');
INSERT INTO public.product_image
(id, product_id, storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('product_image_id_sequence'), 't2b7596b-306c-4ab9-a623-c835e421d533', 100011, 'huseyinari', '2025-07-27 02:05:51.823', 'huseyinari', '2025-07-27 02:05:51.823');
INSERT INTO public.product_image
(id, product_id, storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('product_image_id_sequence'), 'a2b7596b-306c-4ab9-a623-c835e421d533', 100012, 'huseyinari', '2025-07-27 02:06:12.630', 'huseyinari', '2025-07-27 02:06:12.630');
INSERT INTO public.product_image
(id, product_id, storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('product_image_id_sequence'), 'q2b7596b-306c-4ab9-a623-c835e421d533', 100013, 'huseyinari', '2025-07-27 02:06:31.574', 'huseyinari', '2025-07-27 02:06:31.574');


/* product inspect */

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.1', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.2', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.3', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.4', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.5', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.6', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.7', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.8', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.9', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.10', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), 'a2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.1', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), 'a2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.2', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), 'a2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.3', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), 'a2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.4', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), 'a2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.5', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), 'q2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.1', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), 'q2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.2', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), 'q2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.3', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), 't2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.1', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), 't2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.2', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), 't2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.3', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), 't2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.4', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), 't2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.5', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), 't2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.6', now(), 'system');

INSERT INTO public.product_inspect
(id, product_id, inspected_by_ip_address, created_date, created_by)
VALUES(nextval('product_inspects_id_sequence'), 'p9b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.1', now(), 'system');


/* product attributes and product variants */

-- Ürün özellikleri (product_attribute)
INSERT INTO product_attribute (id, name, description, created_by, created_date)
VALUES
(10001, 'Renk', 'Ürün renk seçenekleri', 'system', now()),
(10002, 'Depolama', 'Depolama kapasitesi', 'system', now()),
(10003, 'RAM', 'RAM kapasitesi', 'system', now()),
(10004, 'Beden', 'Ürün bedeni', 'system', now()),
(10005, 'Materyal', 'Ürün materyali', 'system', now()),
(10006, 'Format', 'Ürün formatı', 'system', now()),
(10007, 'Ağırlık', 'Ürün ağırlığı', 'system', now());

-- Ürün özellik değerleri (product_attribute_value)
INSERT INTO product_attribute_value (id, product_id, product_attribute_id, attribute_value, created_by, created_date)
VALUES
-- iPhone 14
(801, '7db7596b-306c-4ab9-a623-c835e421d533', 10001, 'Siyah', 'system', now()),
(802, '7db7596b-306c-4ab9-a623-c835e421d533', 10002, '128GB', 'system', now()),
(803, '7db7596b-306c-4ab9-a623-c835e421d533', 10003, '6GB', 'system', now()),

-- MacBook Air 2025
(804, 'a2b7596b-306c-4ab9-a623-c835e421d533', 10001, 'Gümüş', 'system', now()),
(805, 'a2b7596b-306c-4ab9-a623-c835e421d533', 10002, '256GB', 'system', now()),
(806, 'a2b7596b-306c-4ab9-a623-c835e421d533', 10003, '16GB', 'system', now()),

-- Nike Spor Ayakkabı
(807, 'b4b7596b-306c-4ab9-a623-c835e421d533', 10001, 'Siyah', 'system', now()),
(808, 'b4b7596b-306c-4ab9-a623-c835e421d533', 10004, '42', 'system', now()),
(809, 'b4b7596b-306c-4ab9-a623-c835e421d533', 10005, 'Tekstil', 'system', now()),

-- Sagopa Kajmer Albüm
(810, 'c6b7596b-306c-4ab9-a623-c835e421d533', 10006, 'CD', 'system', now()),

-- Seiko Saat
(811, 'u7b7596b-306c-4ab9-a623-c835e421d533', 10001, 'Gümüş', 'system', now()),
(812, 'u7b7596b-306c-4ab9-a623-c835e421d533', 10005, 'Çelik', 'system', now()),

-- Sinangil Un
(813, 'p9b7596b-306c-4ab9-a623-c835e421d533', 10007, '1 KG', 'system', now()),

-- Samsung Tablet
(814, 'q2b7596b-306c-4ab9-a623-c835e421d533', 10001, 'Siyah', 'system', now()),
(815, 'q2b7596b-306c-4ab9-a623-c835e421d533', 10002, '64GB', 'system', now()),
(816, 'q2b7596b-306c-4ab9-a623-c835e421d533', 10003, '4GB', 'system', now()),

-- Lenovo Mouse
(817, 't2b7596b-306c-4ab9-a623-c835e421d533', 10001, 'Siyah', 'system', now());

-- Varyant tanımları (product_variant)
INSERT INTO product_variant (id, name, description, data_type, ui_component, created_by, created_date)
VALUES
(801, 'Renk Seçimi', 'Ürün renk seçenekleri', 'STRING', 'SELECT', 'system', now()),
(802, 'Depolama Seçimi', 'Depolama seçenekleri', 'STRING', 'SELECT', 'system', now()),
(803, 'Beden Seçimi', 'Beden seçenekleri', 'STRING', 'SELECT', 'system', now()),
(804, 'Format Seçimi', 'Format seçenekleri', 'STRING', 'SELECT', 'system', now());

-- Varyant seçenekleri (product_variant_option)
INSERT INTO product_variant_option (id, product_variant_id, option_value, created_by, created_date)
VALUES
-- Renk seçenekleri
(1351, 801, 'Siyah', 'system', now()),
(1352, 801, 'Beyaz', 'system', now()),
(1353, 801, 'Gümüş', 'system', now()),
(1354, 801, 'Mavi', 'system', now()),
-- Depolama seçenekleri
(1355, 802, '64GB', 'system', now()),
(1356, 802, '128GB', 'system', now()),
(1357, 802, '256GB', 'system', now()),
(1358, 802, '512GB', 'system', now()),
-- Beden seçenekleri
(1359, 803, '40', 'system', now()),
(1360, 803, '41', 'system', now()),
(1361, 803, '42', 'system', now()),
(1362, 803, '43', 'system', now()),
-- Format seçenekleri
(1363, 804, 'CD', 'system', now()),
(1364, 804, 'Vinyl', 'system', now()),
(1365, 804, 'Digital', 'system', now());

-- Ürün varyant değerleri (product_variant_value)
INSERT INTO product_variant_value (id, product_id, product_variant_id, variant_value, sku_code, price, discount, discounted_price, created_by, created_date)
VALUES
-- iPhone 14 varyantları
(45001, '7db7596b-306c-4ab9-a623-c835e421d533', 801, 'Siyah', 'iphone_14_black_128', 50000.00, 0, 50000.00, 'system', now()),
(45002, '7db7596b-306c-4ab9-a623-c835e421d533', 801, 'Beyaz', 'iphone_14_white_128', 50000.00, 0, 50000.00, 'system', now()),
(45003, '7db7596b-306c-4ab9-a623-c835e421d533', 802, '128GB', 'iphone_14_black_128', 50000.00, 0, 50000.00, 'system', now()),
(45004, '7db7596b-306c-4ab9-a623-c835e421d533', 802, '256GB', 'iphone_14_black_256', 55000.00, 0, 55000.00, 'system', now()),

-- MacBook Air varyantları
(45005, 'a2b7596b-306c-4ab9-a623-c835e421d533', 801, 'Gümüş', 'macbook_air_2025_silver', 65000.00, 500, 64500.00, 'system', now()),
(45006, 'a2b7596b-306c-4ab9-a623-c835e421d533', 801, 'Uzay Grisi', 'macbook_air_2025_gray', 65000.00, 500, 64500.00, 'system', now()),

-- Nike Spor Ayakkabı varyantları
(45007, 'b4b7596b-306c-4ab9-a623-c835e421d533', 801, 'Siyah', 'nike_spor_black_42', 4500.00, 0, 4500.00, 'system', now()),
(45008, 'b4b7596b-306c-4ab9-a623-c835e421d533', 803, '42', 'nike_spor_black_42', 4500.00, 0, 4500.00, 'system', now()),
(45009, 'b4b7596b-306c-4ab9-a623-c835e421d533', 803, '43', 'nike_spor_black_43', 4500.00, 0, 4500.00, 'system', now()),

-- Sagopa Albüm varyantları
(45010, 'c6b7596b-306c-4ab9-a623-c835e421d533', 804, 'CD', 'sagopa_kajmer_bpm_cd', 2000.00, 0, 2000.00, 'system', now()),
(45011, 'c6b7596b-306c-4ab9-a623-c835e421d533', 804, 'Digital', 'sagopa_kajmer_bpm_digital', 1500.00, 0, 1500.00, 'system', now()),

-- Samsung Tablet varyantları
(45012, 'q2b7596b-306c-4ab9-a623-c835e421d533', 801, 'Siyah', 'samsung_tab_a9_black_64', 4319.00, 0, 4319.00, 'system', now()),
(45013, 'q2b7596b-306c-4ab9-a623-c835e421d533', 802, '64GB', 'samsung_tab_a9_black_64', 4319.00, 0, 4319.00, 'system', now()),

-- Lenovo Mouse varyantları
(45014, 't2b7596b-306c-4ab9-a623-c835e421d533', 801, 'Siyah', 'lenovo_400_black', 299.00, 100, 399.00, 'system', now());
