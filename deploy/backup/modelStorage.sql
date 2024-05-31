--
-- PostgreSQL database dump
--

-- Dumped from database version 14.10
-- Dumped by pg_dump version 14.10

-- Started on 2024-05-20 00:18:37

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- CREATE SCHEMA public;


-- ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 3328 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

-- SET default_table_access_method = heap;

--
-- TOC entry 210 (class 1259 OID 19393)
-- Name: edges; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.edges (
    id character varying(255) NOT NULL,
    model_name character varying(255) NOT NULL,
    source character varying(255),
    target character varying(255),
    system_type character varying(255),
    length integer,
    selected boolean DEFAULT false,
    equipment jsonb,
    name character varying(255),
    locked boolean
);


ALTER TABLE public.edges OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 19388)
-- Name: model; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.model (
    name character varying(255) NOT NULL
);


ALTER TABLE public.model OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 19406)
-- Name: nodes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nodes (
    id character varying(255) NOT NULL,
    model_name character varying(255) NOT NULL,
    node_type character varying(255),
    system_type character varying(255),
    grouped character varying(255),
    group_name character varying(255),
    "position" jsonb,
    selected boolean DEFAULT false,
    equipment jsonb,
    name character varying(255),
    locked boolean
);


ALTER TABLE public.nodes OWNER TO postgres;

--
-- TOC entry 3321 (class 0 OID 19393)
-- Dependencies: 210
-- Data for Name: edges; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.edges (id, model_name, source, target, system_type, length, selected, equipment, name, locked) FROM stdin;
e12	Две_системы	n1	n2	heat	200	f	[{"id": 1, "installed": true}]	e12	f
e23	Две_системы	n2	n3	heat	20	f	[{"id": 2, "installed": true}]	e23	f
e24	Две_системы	n2	n4	heat	30	f	[{"id": 1, "installed": true}]	e24	f
e56	Две_системы	n5	n6	power	200	f	[{"id": 1, "installed": true}]	e56	f
e67	Две_системы	n6	n7	power	20	f	[{"id": 2, "installed": true}]	e67	f
e58	Две_системы	n6	n8	power	30	f	[{"id": 1, "installed": true}]	e58	f
e61	Две_системы	n6	n1	power	20	f	[{"id": 2, "installed": true}]	e61	f
e16	Пример_всех_элементов	n1	n6	heat	20	f	[{"id": 0, "installed": false}, {"id": 0, "installed": false}, {"id": 0, "installed": false}]	e16	f
e611	Пример_всех_элементов	n6	n11	heat	20	t	[{"id": 0, "installed": false}, {"id": 0, "installed": false}, {"id": 0, "installed": false}]	e611	f
e27	Пример_всех_элементов	n2	n7	power	20	f	[{"id": 0, "installed": false}, {"id": 0, "installed": false}, {"id": 0, "installed": false}]	e27	f
e712	Пример_всех_элементов	n7	n12	power	20	f	[{"id": 0, "installed": false}, {"id": 0, "installed": false}, {"id": 0, "installed": false}]	e712	f
e38	Пример_всех_элементов	n3	n8	fuel	20	f	[{"id": 0, "installed": false}, {"id": 0, "installed": false}, {"id": 0, "installed": false}]	e38	f
e813	Пример_всех_элементов	n8	n13	fuel	20	f	[{"id": 0, "installed": false}, {"id": 0, "installed": false}, {"id": 0, "installed": false}]	e813	f
e49	Пример_всех_элементов	n4	n9	cold	20	f	[{"id": 0, "installed": false}, {"id": 0, "installed": false}, {"id": 0, "installed": false}]	e49	f
e914	Пример_всех_элементов	n9	n14	cold	20	f	[{"id": 0, "installed": false}, {"id": 0, "installed": false}, {"id": 0, "installed": false}]	e914	f
e510	Пример_всех_элементов	n5	n10	other	20	f	[{"id": 0, "installed": false}, {"id": 0, "installed": false}, {"id": 0, "installed": false}]	e510	f
e1015	Пример_всех_элементов	n10	n15	other	20	f	[{"id": 0, "installed": false}, {"id": 0, "installed": false}, {"id": 0, "installed": false}]	e1015	f
e12	Выбор_источника	n1	n2	heat	200	f	[{"id": 1, "installed": true}]	e12	f
e23	Выбор_источника	n2	n3	heat	20	f	[{"id": 2, "installed": true}]	e23	f
e24	Выбор_источника	n2	n4	heat	30	f	[{"id": 1, "installed": true}]	e24	f
e12	Выбор_трубы	n1	n2	heat	20	f	[{"id": 0, "installed": false}, {"id": 0, "installed": false}, {"id": 0, "installed": false}]	e12	f
e23	Выбор_трубы	n2	n3	heat	20	f	[{"id": 0, "installed": false}]	e23	f
e24	Выбор_трубы	n2	n4	heat	20	t	[{"id": 0, "installed": false}]	e24	f
e16	Две_системы_простая	n1	n6	heat	20	f	[{"id": 0, "installed": true}]	e16	f
e62	Две_системы_простая	n6	n2	heat	20	f	[{"id": 0, "installed": true}]	e62	f
e35	Две_системы_простая	n3	n5	power	20	f	[{"id": 0, "installed": true}]	e35	f
e54	Две_системы_простая	n5	n4	power	20	f	[{"id": 0, "installed": true}]	e54	f
e51	Две_системы_простая	n5	n1	power	20	f	[{"id": 0, "installed": true}]	e51	f
\.


--
-- TOC entry 3320 (class 0 OID 19388)
-- Dependencies: 209
-- Data for Name: model; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.model (name) FROM stdin;
Две_системы
Пример_всех_элементов
Выбор_источника
Выбор_трубы
Две_системы_простая
\.


--
-- TOC entry 3322 (class 0 OID 19406)
-- Dependencies: 211
-- Data for Name: nodes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.nodes (id, model_name, node_type, system_type, grouped, group_name, "position", selected, equipment, name, locked) FROM stdin;
n1	Выбор_источника	source	heat	\N	\N	{"x": 0.0, "y": -100.0}	f	[{"id": 1, "installed": false}, {"id": 2, "installed": false}]	n1	f
n2	Выбор_источника	connector	heat	\N	\N	{"x": 100.0, "y": -100.0}	f	[{"id": 0, "installed": true}]	n2	f
n3	Выбор_источника	consumer	heat	\N	\N	{"x": 200.0, "y": -50.0}	f	[{"id": 1, "installed": true}]	n3	f
n4	Выбор_источника	consumer	heat	\N	\N	{"x": 200.0, "y": -150.0}	f	[{"id": 1, "installed": true}]	n4	f
n1	Выбор_трубы	source	heat	\N	\N	{"x": 0.0, "y": -100.0}	f	[{"id": 0, "installed": false}]	n1	f
n2	Выбор_трубы	connector	power	\N	\N	{"x": 100.0, "y": -100.0}	f	[{"id": 0, "installed": false}]	n2	f
n3	Выбор_трубы	consumer	heat	\N	\N	{"x": 200.0, "y": -50.0}	f	[{"id": 0, "installed": false}]	n3	f
n4	Выбор_трубы	consumer	heat	\N	\N	{"x": 200.0, "y": -150.0}	f	[{"id": 0, "installed": false}]	n4	f
n1	Две_системы	source	heat	false		{"x": 0.0, "y": -100.0}	f	[{"id": 1, "installed": false}, {"id": 4, "installed": false}]	n1	f
n2	Две_системы	connector	heat	false		{"x": 100.0, "y": -100.0}	f	[{"id": 0, "installed": true}]	n2	f
n3	Две_системы_простая	source	power	\N		{"x": -117.97755921365723, "y": -285.6263285702084}	f	[{"id": 1, "installed": true}]	n3	f
n4	Две_системы_простая	consumer	power	\N		{"x": -15.613796385556864, "y": -287.2257623643973}	f	[{"id": 1, "installed": true}]	n4	f
n1	Пример_всех_элементов	source	heat	\N	\N	{"x": 0.0, "y": -100.0}	f	[{"id": 0, "installed": false}]	n1	f
n2	Пример_всех_элементов	source	power	\N	\N	{"x": 50.0, "y": -100.0}	f	[{"id": 0, "installed": false}]	n2	f
n3	Пример_всех_элементов	source	fuel	\N	\N	{"x": 100.0, "y": -100.0}	f	[{"id": 0, "installed": false}]	n3	f
n4	Пример_всех_элементов	source	cold	\N	\N	{"x": 150.0, "y": -100.0}	f	[{"id": 0, "installed": false}]	n4	f
n5	Пример_всех_элементов	source	other	\N	\N	{"x": 200.0, "y": -100.0}	f	[{"id": 0, "installed": false}]	n5	f
n3	Две_системы	consumer	heat	false		{"x": 200.0, "y": -50.0}	f	[{"id": 1, "installed": true}]	n3	f
n4	Две_системы	consumer	heat	true	gr	{"x": 200.0, "y": -150.0}	f	[{"id": 1, "installed": true}]	n4	f
n5	Две_системы	source	power	false		{"x": -23.200000000000006, "y": -258.40000000000003}	f	[{"id": 1, "installed": true}]	n5	f
n6	Две_системы	connector	power	false		{"x": 76.80000000000001, "y": -231.20000000000005}	f	[{"id": 0, "installed": true}]	n6	f
n7	Две_системы	consumer	power	true	gr	{"x": 211.99999999999997, "y": -204.39999999999998}	f	[{"id": 1, "installed": true}]	n7	f
n8	Две_системы	consumer	power	false		{"x": 195.2, "y": -270.0}	f	[{"id": 1, "installed": true}]	n8	f
n5	Две_системы_простая	connector	power	false		{"x": -66.6971435546875, "y": -279.2}	f	[{"id": 0, "installed": true}]	n5	f
n6	Две_системы_простая	connector	heat	false		{"x": -94.6971435546875, "y": -200.0}	f	[{"id": 0, "installed": true}]	n6	f
n6	Пример_всех_элементов	connector	heat	\N	\N	{"x": 0.0, "y": -50.0}	f	[{"id": 0, "installed": false}]	n6	f
n7	Пример_всех_элементов	connector	power	\N	\N	{"x": 50.0, "y": -50.0}	f	[{"id": 0, "installed": false}]	n7	f
n8	Пример_всех_элементов	connector	fuel	\N	\N	{"x": 100.0, "y": -50.0}	f	[{"id": 0, "installed": false}]	n8	f
n9	Пример_всех_элементов	connector	cold	\N	\N	{"x": 150.0, "y": -50.0}	f	[{"id": 0, "installed": false}]	n9	f
n10	Пример_всех_элементов	connector	other	\N	\N	{"x": 200.0, "y": -50.0}	f	[{"id": 0, "installed": false}]	n10	f
n11	Пример_всех_элементов	consumer	heat	\N	\N	{"x": 0.0, "y": 0.0}	f	[{"id": 0, "installed": false}]	n11	f
n12	Пример_всех_элементов	consumer	power	\N	\N	{"x": 50.0, "y": 0.0}	f	[{"id": 0, "installed": false}]	n12	f
n13	Пример_всех_элементов	consumer	fuel	\N	\N	{"x": 100.0, "y": 0.0}	f	[{"id": 0, "installed": false}]	n13	f
n14	Пример_всех_элементов	consumer	cold	\N	\N	{"x": 150.0, "y": 0.0}	f	[{"id": 0, "installed": false}]	n14	f
n15	Пример_всех_элементов	consumer	other	\N	\N	{"x": 200.0, "y": 0.0}	f	[{"id": 0, "installed": false}]	n15	f
n16	Пример_всех_элементов	node	node	\N	\N	{"x": 250.0, "y": -50.0}	f	[{"id": 0, "installed": false}]	n16	f
n1	Две_системы_простая	source	heat	\N		{"x": -154.76453648000592, "y": -197.6574698898095}	t	[{"id": 1, "installed": true}]	n1	f
n2	Две_системы_простая	consumer	heat	\N		{"x": -33.20756812163663, "y": -200.85633747818764}	f	[{"id": 1, "installed": true}]	n2	f
\.


--
-- TOC entry 3176 (class 2606 OID 19400)
-- Name: edges edges_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.edges
    ADD CONSTRAINT edges_pkey PRIMARY KEY (id, model_name);


--
-- TOC entry 3174 (class 2606 OID 19392)
-- Name: model model_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.model
    ADD CONSTRAINT model_pkey PRIMARY KEY (name);


--
-- TOC entry 3178 (class 2606 OID 19413)
-- Name: nodes nodes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nodes
    ADD CONSTRAINT nodes_pkey PRIMARY KEY (id, model_name);


--
-- TOC entry 3179 (class 2606 OID 19401)
-- Name: edges edges_model_name_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.edges
    ADD CONSTRAINT edges_model_name_fkey FOREIGN KEY (model_name) REFERENCES public.model(name) ON DELETE CASCADE;


--
-- TOC entry 3180 (class 2606 OID 19414)
-- Name: nodes nodes_model_name_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nodes
    ADD CONSTRAINT nodes_model_name_fkey FOREIGN KEY (model_name) REFERENCES public.model(name) ON DELETE CASCADE;


-- Completed on 2024-05-20 00:18:38

--
-- PostgreSQL database dump complete
--

