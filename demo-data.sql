--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1
-- Dumped by pg_dump version 13.1

-- Started on 2022-07-15 01:53:20 +01


--
-- TOC entry 3277 (class 0 OID 67798)
-- Dependencies: 203
-- Data for Name: app_user; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.app_user VALUES (111, true, NULL, '2022-07-15 00:46:29.523603', 's.hassan@cargoship.co', 'Soufi', NULL, 'Hassan', '$2a$10$GgqD/bsl2QYIdRYNRrN2febSiav8KiDbKSeUojuMvHlXCrXHhnT0m', '00212701187267', NULL, 0, 's.hassan', 106);
INSERT INTO public.app_user VALUES (112, true, NULL, '2022-07-15 00:48:14.2355', 'a.aitabd@wharf.ma', 'Ahmed', NULL, 'AIT ABD', '$2a$10$E/ben3G5GW3i66x1rH66neeLHDvzltVwAi05qfgt3mKUbvHcxszA2', '00212676545434', NULL, 0, 'a.aitabd', 103);
INSERT INTO public.app_user VALUES (113, true, NULL, '2022-07-15 00:49:33.644417', 'p.gostaf@cargoship.co', 'Paul', NULL, 'Gostaf', '$2a$10$QothXIztxCoun1whYIxmuuLpOMC8wURExIw5KdCR55HWf6igMhZx.', '00212876268712', NULL, 0, 'p.gostaf', 106);
INSERT INTO public.app_user VALUES (114, true, NULL, '2022-07-15 00:52:25.603116', 's.conate@milwaukee.ma', 'Samba', NULL, 'Conate', '$2a$10$sTWgf5lIKJRNlaVessJomukCy1mj7GGgf0rbnjZlnCBJtiZ7qZmZO', '00212876376777', NULL, 0, 's.conate', 104);
INSERT INTO public.app_user VALUES (115, true, NULL, '2022-07-15 00:53:23.570368', 's.hamouti@ceva.com', 'Salma', NULL, 'Hamouti', '$2a$10$5R4z795GCvTZOL4Ck5BgaumcEvFQ2m4vIviJxB775fJBLoMCVPBhK', '00212987876545', NULL, 0, 's.hamouti', 109);
INSERT INTO public.app_user VALUES (101, true, NULL, '2022-07-15 00:17:10.412466', 'admin@local', 'Admin', 'en', 'APP', '$2a$10$GgqD/bsl2QYIdRYNRrN2febSiav8KiDbKSeUojuMvHlXCrXHhnT0m', '00212600000000', NULL, 1, 'admin', 100);


--
-- TOC entry 3278 (class 0 OID 67806)
-- Dependencies: 204
-- Data for Name: inventory_item; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.inventory_item VALUES (116, '2022-07-15 01:06:14.771114', false, '2022-07-15 01:14:53.785545', 'China Steel Co.', 'RH374911285CN', 3999.8, 'RH374911285CN', NULL, '821661117090', 106, 111);
INSERT INTO public.inventory_item VALUES (117, '2022-07-15 01:07:08.686244', false, '2022-07-15 01:14:59.130484', 'China Steel Co.', 'RH728936294CN', 8799.5, 'RH728936294CN', NULL, '845242483194', 106, 111);
INSERT INTO public.inventory_item VALUES (121, '2022-07-15 01:09:47.114928', false, '2022-07-15 01:15:00.600157', 'Toys R Us', 'ZF371724120US', 2989, 'ZF371724120US', NULL, '411646472004', 106, 111);
INSERT INTO public.inventory_item VALUES (118, '2022-07-15 01:07:37.667712', true, NULL, 'China Steel Co.', 'RH317567310CN', 8588, 'RH317567310CN', NULL, '874486584455', 106, 111);
INSERT INTO public.inventory_item VALUES (120, '2022-07-15 01:09:24.335132', true, NULL, 'Toys R Us', 'ZF435477644US', 3499, 'ZF435477644US', NULL, '421295691914', 106, 111);
INSERT INTO public.inventory_item VALUES (123, '2022-07-15 01:13:28.568109', true, NULL, 'Chanel PB', 'MS209359255FR', 40899, 'MS209359255FR', NULL, '247656853884', 106, 113);
INSERT INTO public.inventory_item VALUES (119, '2022-07-15 01:08:10.405696', true, NULL, 'China Steel Co.', 'RH740576197CN', 9810.99, 'RH740576197CN', NULL, '957357070718', 106, 111);
INSERT INTO public.inventory_item VALUES (122, '2022-07-15 01:10:13.596501', false, '2022-07-15 01:16:43.938536', 'Toys R Us', 'ZF880032284US', 1999.5, 'ZF880032284US', NULL, '444505674873', 106, 111);
INSERT INTO public.inventory_item VALUES (125, '2022-07-15 01:14:14.910156', false, '2022-07-15 01:16:45.036009', 'Chanel PB', 'MS924590690FR', 23599, 'MS924590690FR', NULL, '268597638903', 106, 113);
INSERT INTO public.inventory_item VALUES (124, '2022-07-15 01:13:52.364064', true, NULL, 'Chanel PB', 'MS145380765FR', 39877, 'MS145380765FR', NULL, '204067209752', 106, 113);


--
-- TOC entry 3279 (class 0 OID 67814)
-- Dependencies: 205
-- Data for Name: shipment; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.shipment VALUES (127, '2022-07-15 01:20:54.708195', false, 'EV938507560CD', 'FEDEX EXPRESS', '121', 113, 106);
INSERT INTO public.shipment VALUES (126, '2022-07-15 01:20:28.861176', false, 'UQ252246764SG', 'FEDEX EXPRESS', '125', 113, 106);
INSERT INTO public.shipment VALUES (128, '2022-07-15 01:22:12.804743', false, 'BELBE0110325180YQ', 'BOLORE LOGISTICS', '116', 113, 106);
INSERT INTO public.shipment VALUES (129, '2022-07-15 01:22:23.826565', false, 'UQ252246764SG', 'BOLORE LOGISTICS', '117', 113, 106);
INSERT INTO public.shipment VALUES (130, '2022-07-15 01:23:22.506489', false, 'EV938507560PC', 'UPS', '122', 113, 106);
INSERT INTO public.shipment VALUES (131, '2022-07-15 01:26:04.706258', true, 'EV264677041US', 'UPS', '118', 113, 106);
INSERT INTO public.shipment VALUES (132, '2022-07-15 01:26:20.457989', true, 'EV601259583US', 'UPS', '120', 113, 106);
INSERT INTO public.shipment VALUES (133, '2022-07-15 01:26:36.343646', true, 'EV213119812US', 'UPS', '123', 113, 106);
INSERT INTO public.shipment VALUES (134, '2022-07-15 01:26:58.541256', true, 'EV122624266FR', 'FEDEX EXPRESS', '119', 113, 106);
INSERT INTO public.shipment VALUES (135, '2022-07-15 01:27:17.605903', true, 'PZ049113745EN', 'FEDEX EXPRESS', '124', 113, 106);


--
-- TOC entry 3280 (class 0 OID 67822)
-- Dependencies: 206
-- Data for Name: warehouse; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.warehouse VALUES (100, '2022-07-15 00:17:10.253568', '0,0', 'Default');
INSERT INTO public.warehouse VALUES (102, NULL, '33.9181828,-7.0150731', 'The Black Craft');
INSERT INTO public.warehouse VALUES (103, NULL, '33.6079633,-7.590151', 'Wharf Container Terminal');
INSERT INTO public.warehouse VALUES (104, NULL, '33.6036509,-7.5815626', 'Milwaukee Maroc');
INSERT INTO public.warehouse VALUES (105, NULL, '33.5911063,-7.5862816', 'Maroc Express');
INSERT INTO public.warehouse VALUES (106, NULL, '33.5953604,-7.603312', 'Cargoship Co.');
INSERT INTO public.warehouse VALUES (107, NULL, '33.5852102,-7.5626387', 'Taghazout Warehouse');
INSERT INTO public.warehouse VALUES (108, NULL, '33.597752,-7.6336071', 'Bazar 67');
INSERT INTO public.warehouse VALUES (109, NULL, '33.6005081,-7.553065', 'Ceva logistics');
INSERT INTO public.warehouse VALUES (110, NULL, '33.5865012,-7.6252461', 'Medex express');

