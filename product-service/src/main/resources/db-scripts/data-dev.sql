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
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(100, 'Yüz Tanım', 'faceRecognition', 'Üründe yüz tanıma özelliği var mı ?', 'huseyinari', '2025-10-25 12:51:41.385', 'huseyinari', '2025-10-25 12:51:41.385');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(101, 'İşlemci Türü', 'cpuType', 'Üründe kullanılan işlemci', 'huseyinari', '2025-10-25 12:53:32.136', 'huseyinari', '2025-10-25 12:53:32.136');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(102, 'İşlemci Hızı (GHz)', 'cpuSpeed', 'Üründe kullanılan işlemcinin hızı', 'huseyinari', '2025-10-25 12:54:06.561', 'huseyinari', '2025-10-25 12:54:06.561');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(103, 'İşlemci Çekirdek Sayısı', 'cpuCount', 'Üründe kullanılan işlemci çekirdek sayısı', 'huseyinari', '2025-10-25 12:55:24.278', 'huseyinari', '2025-10-25 12:55:24.278');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(104, 'Ekran Boyutu (inch)', 'screenSize', 'Ekran boyutu', 'huseyinari', '2025-10-25 12:56:07.511', 'huseyinari', '2025-10-25 12:56:07.511');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(105, 'Ekran Tipi', 'screenType', 'Cihazda kullanılan ekranın türü', 'huseyinari', '2025-10-25 12:56:51.096', 'huseyinari', '2025-10-25 12:56:51.096');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(106, 'Ekran Yenileme Hızı (Hz)', 'screenHz', 'Ekran yenileme hızı', 'huseyinari', '2025-10-25 12:57:50.901', 'huseyinari', '2025-10-25 12:57:50.901');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(107, 'Arka kamera sayısı', 'rearCameraCount', 'Arka kamera sayısı', 'huseyinari', '2025-10-25 12:59:11.672', 'huseyinari', '2025-10-25 12:59:11.672');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(108, 'Arka Kamera Çözünürlüğü', 'rearCameraResolution', 'Arka kamera çözünürlüğü', 'huseyinari', '2025-10-25 12:58:31.924', 'huseyinari', '2025-10-25 12:58:31.924');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(109, 'Ön kamera çözünürlüğü', 'frontCameraResolution', 'Ön kamera çözünürlüğü', 'huseyinari', '2025-10-25 13:11:23.299', 'huseyinari', '2025-10-25 13:11:23.299');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(110, 'Wi-Fi', 'wifi', 'Cihazda Wifi var mı ?', 'huseyinari', '2025-10-25 13:13:25.974', 'huseyinari', '2025-10-25 13:13:25.974');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(111, 'Bluetooth', 'bluetooth', 'Cihazda Bluetooth var mı ?', 'huseyinari', '2025-10-25 13:13:58.713', 'huseyinari', '2025-10-25 13:13:58.713');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(112, 'Şarj Girişi', 'chargingPort', 'Cihaz şarj soketi tipi', 'huseyinari', '2025-10-25 13:14:37.427', 'huseyinari', '2025-10-25 13:14:37.427');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(113, 'Pil Gücü', 'batteryPower', 'Batarya gücü', 'huseyinari', '2025-10-25 13:15:13.213', 'huseyinari', '2025-10-25 13:15:13.213');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(114, 'Kablosuz Şarj', 'wirelessCharging', 'Kablosuz Şarj', 'huseyinari', '2025-10-25 13:18:04.658', 'huseyinari', '2025-10-25 13:18:04.658');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(115, 'Hızlı Şarj', 'quickCharging', 'Hızlı Şarj', 'huseyinari', '2025-10-25 13:18:24.309', 'huseyinari', '2025-10-25 13:18:24.309');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(116, 'Garanti Süresi (Ay)', 'warrantyPeriod', 'Garanti süresi', 'huseyinari', '2025-10-25 13:19:40.422', 'huseyinari', '2025-10-25 13:19:40.422');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(117, 'Dahili Hafıza', 'internalDisk', 'Dahili hafıza', 'huseyinari', '2025-10-25 13:20:38.536', 'huseyinari', '2025-10-25 13:20:38.536');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(118, 'İşletim Sistemi', 'os', 'İşletim sistemi', 'huseyinari', '2025-10-25 13:21:08.770', 'huseyinari', '2025-10-25 13:21:08.770');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(119, 'Ağırlık', 'weight', 'Ağırlık', 'huseyinari', '2025-10-25 13:22:10.515', 'huseyinari', '2025-10-25 13:22:10.515');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(120, 'Genişlik', 'width', 'Genişlik', 'huseyinari', '2025-10-25 13:22:35.596', 'huseyinari', '2025-10-25 13:22:35.596');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(121, 'Kalınlık', 'thick', 'Kalınlık', 'huseyinari', '2025-10-25 13:23:09.381', 'huseyinari', '2025-10-25 13:23:09.381');
INSERT INTO public.product_attribute
(id, "name", query_name, description, created_by, created_date, updated_by, updated_date)
VALUES(122, 'Yükseklik', 'height', 'Yükseklik', 'huseyinari', '2025-10-25 13:23:26.835', 'huseyinari', '2025-10-25 13:23:26.835');
