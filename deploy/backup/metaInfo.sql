--
-- PostgreSQL database dump
--

-- Dumped from database version 14.10
-- Dumped by pg_dump version 14.10

-- Started on 2024-05-19 23:51:17

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
-- TOC entry 3333 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

-- SET default_table_access_method = heap;

--
-- TOC entry 210 (class 1259 OID 18835)
-- Name: consumers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.consumers (
    id integer NOT NULL,
    type character varying(255),
    name character varying(255),
    load integer
);


ALTER TABLE public.consumers OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 18834)
-- Name: consumers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.consumers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.consumers_id_seq OWNER TO postgres;

--
-- TOC entry 3334 (class 0 OID 0)
-- Dependencies: 209
-- Name: consumers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.consumers_id_seq OWNED BY public.consumers.id;


--
-- TOC entry 214 (class 1259 OID 18872)
-- Name: lines; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lines (
    id integer NOT NULL,
    type character varying(255),
    name character varying(255),
    throughput integer,
    resistance integer,
    cost double precision
);


ALTER TABLE public.lines OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 18871)
-- Name: lines_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.lines_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.lines_id_seq OWNER TO postgres;

--
-- TOC entry 3335 (class 0 OID 0)
-- Dependencies: 213
-- Name: lines_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.lines_id_seq OWNED BY public.lines.id;


--
-- TOC entry 212 (class 1259 OID 18844)
-- Name: sources; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sources (
    id integer NOT NULL,
    type character varying(255),
    name character varying(255),
    max_gen integer,
    min_gen integer,
    price double precision,
    cost double precision,
    efficiency double precision
);


ALTER TABLE public.sources OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 18843)
-- Name: sources_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sources_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sources_id_seq OWNER TO postgres;

--
-- TOC entry 3336 (class 0 OID 0)
-- Dependencies: 211
-- Name: sources_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sources_id_seq OWNED BY public.sources.id;


--
-- TOC entry 3174 (class 2604 OID 18838)
-- Name: consumers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consumers ALTER COLUMN id SET DEFAULT nextval('public.consumers_id_seq'::regclass);


--
-- TOC entry 3176 (class 2604 OID 18875)
-- Name: lines id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lines ALTER COLUMN id SET DEFAULT nextval('public.lines_id_seq'::regclass);


--
-- TOC entry 3175 (class 2604 OID 18847)
-- Name: sources id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sources ALTER COLUMN id SET DEFAULT nextval('public.sources_id_seq'::regclass);


--
-- TOC entry 3323 (class 0 OID 18835)
-- Dependencies: 210
-- Data for Name: consumers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.consumers (id, type, name, load) FROM stdin;
1	heat	Дом №1	10
2	heat	Дом №2	20
3	heat	Дом №3	30
4	power	Дом №1	10
5	power	Дом №2	20
6	power	Дом №3	30
\.


--
-- TOC entry 3327 (class 0 OID 18872)
-- Dependencies: 214
-- Data for Name: lines; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.lines (id, type, name, throughput, resistance, cost) FROM stdin;
1	heat	Труба №1	10	1	200
2	heat	Труба №2	20	4	100
3	heat	Труба №3	30	2	400
4	power	Линия №1	10	5	350
5	power	Линия №2	20	4	130
6	power	Линия №3	30	3	220
\.


--
-- TOC entry 3325 (class 0 OID 18844)
-- Dependencies: 212
-- Data for Name: sources; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sources (id, type, name, max_gen, min_gen, price, cost, efficiency) FROM stdin;
1	heat	ТЭЦ	10	10	0	100000	0.95
2	heat	ТЭЦ	60	10	0	200000	0.95
3	heat	ТЭЦ	50	10	0	300000	0.95
4	power	ТЭЦ	10	10	0	100000	0.95
5	power	ТЭЦ	60	10	0	200000	0.95
6	power	ТЭЦ	50	10	0	300000	0.95
\.


--
-- TOC entry 3337 (class 0 OID 0)
-- Dependencies: 209
-- Name: consumers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.consumers_id_seq', 6, true);


--
-- TOC entry 3338 (class 0 OID 0)
-- Dependencies: 213
-- Name: lines_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.lines_id_seq', 6, true);


--
-- TOC entry 3339 (class 0 OID 0)
-- Dependencies: 211
-- Name: sources_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sources_id_seq', 6, true);


--
-- TOC entry 3178 (class 2606 OID 18842)
-- Name: consumers consumers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consumers
    ADD CONSTRAINT consumers_pkey PRIMARY KEY (id);


--
-- TOC entry 3182 (class 2606 OID 18879)
-- Name: lines lines_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lines
    ADD CONSTRAINT lines_pkey PRIMARY KEY (id);


--
-- TOC entry 3180 (class 2606 OID 18851)
-- Name: sources sources_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sources
    ADD CONSTRAINT sources_pkey PRIMARY KEY (id);


-- Completed on 2024-05-19 23:51:18

--
-- PostgreSQL database dump complete
--

