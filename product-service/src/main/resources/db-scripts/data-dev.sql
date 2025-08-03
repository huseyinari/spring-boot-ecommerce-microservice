/* products */
INSERT INTO public.products
(id, "name", description, sku_code, price, discount, discounted_price, category_id, status, user_id, created_date, created_by)
VALUES('7db7596b-306c-4ab9-a623-c835e421d533', 'Iphone 14', 'Iphone 14 Orjinal Türkiye Garantili', 'iphone_14', 50000.00, 0, 50000, 100011, 'SUCCESS', 'c043d233-6a49-4fd8-96fe-5f8bc96c0bddc043d233-6a49-4fd8-96fe-5f8bc96c0bdd', '2025-07-26T00:00:00', 'system');

INSERT INTO public.products
(id, "name", description, sku_code, price, discount, discounted_price, category_id, status, user_id, created_date, created_by)
VALUES('a2b7596b-306c-4ab9-a623-c835e421d533', 'Macbook Air 2025', 'Macbook Air 2025 Orjinal Türkiye Garantili 16gb RAM 256gb SSD M3 Chip', 'macbook_air_2025', 65000, 500, 64500, 100009, 'SUCCESS', 'c043d233-6a49-4fd8-96fe-5f8bc96c0bddc043d233-6a49-4fd8-96fe-5f8bc96c0bdd', '2025-07-26T00:00:00', 'system');

INSERT INTO public.products
(id, "name", description, sku_code, price, discount, discounted_price, category_id, status, user_id, created_date, created_by)
VALUES('b4b7596b-306c-4ab9-a623-c835e421d533', 'Nike Spor Ayakkabı', 'Nike spor ayakkabı', 'nike_spor_ayakkabi_11', 4500, 0, 4500, 100005, 'SUCCESS', 'c043d233-6a49-4fd8-96fe-5f8bc96c0bddc043d233-6a49-4fd8-96fe-5f8bc96c0bdd', '2025-07-26T00:00:00', 'system');

INSERT INTO public.products
(id, "name", description, sku_code, price, discount, discounted_price, category_id, status, user_id, created_date, created_by)
VALUES('c6b7596b-306c-4ab9-a623-c835e421d533', 'Sagopa Kajmer Bir Pesimistin Gözyaşları Albüm', 'sago albüm', 'sagopa_kajmer_bpm_album', 2000, 0, 2000, 100008, 'SUCCESS', 'c043d233-6a49-4fd8-96fe-5f8bc96c0bddc043d233-6a49-4fd8-96fe-5f8bc96c0bdd', '2025-07-26T00:00:00', 'system');

INSERT INTO public.products
(id, "name", description, sku_code, price, discount, discounted_price, category_id, status, user_id, created_date, created_by)
VALUES('u7b7596b-306c-4ab9-a623-c835e421d533', 'Seiko SRPD63K1 Erkek Kol Saati', 'Kol Saati', 'seiko_srpd63k1', 16079.70, 0, 16079.70, 100001, 'SUCCESS', 'c043d233-6a49-4fd8-96fe-5f8bc96c0bddc043d233-6a49-4fd8-96fe-5f8bc96c0bdd', '2025-07-26T00:00:00', 'system');

INSERT INTO public.products
(id, "name", description, sku_code, price, discount, discounted_price, category_id, status, user_id, created_date, created_by)
VALUES('p9b7596b-306c-4ab9-a623-c835e421d533', 'Sinangil Çok Amaçlı Un 1kg', 'sinangil un', 'sinangil_un_1kg', 132, 0, 132, 100001, 'SUCCESS', 'c043d233-6a49-4fd8-96fe-5f8bc96c0bddc043d233-6a49-4fd8-96fe-5f8bc96c0bdd', '2025-07-26T00:00:00', 'system');

INSERT INTO public.products
(id, "name", description, sku_code, price, discount, discounted_price, category_id, status, user_id, created_date, created_by)
VALUES('q2b7596b-306c-4ab9-a623-c835e421d533', 'Samsung Galaxy Tab A9', 'tablet', 'samsung_galaxy_tab_a9', 4319, 0, 4319, 100009, 'SUCCESS', 'c043d233-6a49-4fd8-96fe-5f8bc96c0bddc043d233-6a49-4fd8-96fe-5f8bc96c0bdd', '2025-07-26T00:00:00', 'system');

INSERT INTO public.products
(id, "name", description, sku_code, price, discount, discounted_price, category_id, status, user_id, created_date, created_by)
VALUES('t2b7596b-306c-4ab9-a623-c835e421d533', 'Lenovo 400 Wireless Kablosuz Mouse Siyah GY50R91293', 'mouse', 'lenovo_400_wireless_kablosuz_siyah_mouse_gy50r91293', 299, 100, 399, 100009, 'SUCCESS', 'c043d233-6a49-4fd8-96fe-5f8bc96c0bddc043d233-6a49-4fd8-96fe-5f8bc96c0bdd', '2025-07-26T00:00:00', 'system');

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


/* product reviews */

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.1', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.2', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.3', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.4', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.5', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.6', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.7', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.8', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.9', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), '7db7596b-306c-4ab9-a623-c835e421d533', '10.100.50.10', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), 'a2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.1', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), 'a2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.2', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), 'a2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.3', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), 'a2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.4', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), 'a2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.5', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), 'q2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.1', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), 'q2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.2', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), 'q2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.3', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), 't2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.1', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), 't2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.2', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), 't2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.3', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), 't2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.4', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), 't2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.5', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), 't2b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.6', now(), 'system');

INSERT INTO public.product_reviews
(id, product_id, reviewed_ip_address, created_date, created_by)
VALUES(nextval('product_reviews_id_sequence'), 'p9b7596b-306c-4ab9-a623-c835e421d533', '10.100.50.1', now(), 'system');

