--
-- PostgreSQL database dump
--

-- Dumped from database version 14.12
-- Dumped by pg_dump version 16.3

-- Started on 2024-08-17 19:11:31

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
-- TOC entry 6 (class 2615 OID 16438)
-- Name: society; Type: SCHEMA; Schema: -; Owner: admin
--

CREATE SCHEMA society;


ALTER SCHEMA society OWNER TO admin;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 241 (class 1259 OID 24948)
-- Name: actionedpaymentsdetails; Type: TABLE; Schema: society; Owner: admin
--

CREATE TABLE society.actionedpaymentsdetails (
    id bigint NOT NULL,
    action character varying(255),
    actionedby character varying(255),
    actionedtimestamp character varying(255),
    actionersuseragentdetails character varying(255),
    amount double precision NOT NULL,
    date date,
    flatnumber integer NOT NULL,
    paymentmethod character varying(255),
    rejectreason character varying(255),
    transactionid character varying(255)
);


ALTER TABLE society.actionedpaymentsdetails OWNER TO admin;

--
-- TOC entry 236 (class 1259 OID 24910)
-- Name: approvalpendingpayments; Type: TABLE; Schema: society; Owner: admin
--

CREATE TABLE society.approvalpendingpayments (
    id bigint NOT NULL,
    amount double precision NOT NULL,
    date date,
    flatnumber integer NOT NULL,
    paymentmethod character varying(255),
    transactionid character varying(255)
);


ALTER TABLE society.approvalpendingpayments OWNER TO admin;

--
-- TOC entry 235 (class 1259 OID 24909)
-- Name: approvalpendingpayments_id_seq; Type: SEQUENCE; Schema: society; Owner: admin
--

CREATE SEQUENCE society.approvalpendingpayments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE society.approvalpendingpayments_id_seq OWNER TO admin;

--
-- TOC entry 3452 (class 0 OID 0)
-- Dependencies: 235
-- Name: approvalpendingpayments_id_seq; Type: SEQUENCE OWNED BY; Schema: society; Owner: admin
--

ALTER SEQUENCE society.approvalpendingpayments_id_seq OWNED BY society.approvalpendingpayments.id;


--
-- TOC entry 237 (class 1259 OID 24918)
-- Name: deletedextracharges; Type: TABLE; Schema: society; Owner: admin
--

CREATE TABLE society.deletedextracharges (
    id bigint NOT NULL,
    chargedamount double precision NOT NULL,
    comments character varying(255),
    deletereasonother character varying(255),
    deletereasonselect character varying(255),
    flatnumber integer NOT NULL,
    reason character varying(255)
);


ALTER TABLE society.deletedextracharges OWNER TO admin;

--
-- TOC entry 239 (class 1259 OID 24926)
-- Name: extrachargesentry; Type: TABLE; Schema: society; Owner: admin
--

CREATE TABLE society.extrachargesentry (
    id bigint NOT NULL,
    chargedamount double precision NOT NULL,
    comments character varying(255),
    flatnumber integer NOT NULL,
    reason character varying(255),
    settled character varying(255)
);


ALTER TABLE society.extrachargesentry OWNER TO admin;

--
-- TOC entry 238 (class 1259 OID 24925)
-- Name: extrachargesentry_id_seq; Type: SEQUENCE; Schema: society; Owner: admin
--

CREATE SEQUENCE society.extrachargesentry_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE society.extrachargesentry_id_seq OWNER TO admin;

--
-- TOC entry 3453 (class 0 OID 0)
-- Dependencies: 238
-- Name: extrachargesentry_id_seq; Type: SEQUENCE OWNED BY; Schema: society; Owner: admin
--

ALTER SEQUENCE society.extrachargesentry_id_seq OWNED BY society.extrachargesentry.id;


--
-- TOC entry 245 (class 1259 OID 24966)
-- Name: extrachargesentryforallflats; Type: TABLE; Schema: society; Owner: admin
--

CREATE TABLE society.extrachargesentryforallflats (
    id bigint NOT NULL,
    chargedamount double precision NOT NULL,
    comments character varying(255),
    flatnumber integer NOT NULL,
    reason character varying(255)
);


ALTER TABLE society.extrachargesentryforallflats OWNER TO admin;

--
-- TOC entry 244 (class 1259 OID 24965)
-- Name: extrachargesentryforallflats_id_seq; Type: SEQUENCE; Schema: society; Owner: admin
--

CREATE SEQUENCE society.extrachargesentryforallflats_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE society.extrachargesentryforallflats_id_seq OWNER TO admin;

--
-- TOC entry 3454 (class 0 OID 0)
-- Dependencies: 244
-- Name: extrachargesentryforallflats_id_seq; Type: SEQUENCE OWNED BY; Schema: society; Owner: admin
--

ALTER SEQUENCE society.extrachargesentryforallflats_id_seq OWNED BY society.extrachargesentryforallflats.id;


--
-- TOC entry 219 (class 1259 OID 16448)
-- Name: flat_type_flat_number_map; Type: TABLE; Schema: society; Owner: admin
--

CREATE TABLE society.flat_type_flat_number_map (
    flatnumber integer NOT NULL,
    annualmaintenance double precision NOT NULL,
    flattype character varying(255)
);


ALTER TABLE society.flat_type_flat_number_map OWNER TO admin;

--
-- TOC entry 240 (class 1259 OID 24934)
-- Name: maintenancemasterentry; Type: TABLE; Schema: society; Owner: admin
--

CREATE TABLE society.maintenancemasterentry (
    flatnumber integer NOT NULL,
    chargedamount double precision NOT NULL,
    currentyear character varying(255),
    lastrecieveddate date,
    receivedtillnow double precision NOT NULL,
    totaloutstanding double precision NOT NULL
);


ALTER TABLE society.maintenancemasterentry OWNER TO admin;

--
-- TOC entry 226 (class 1259 OID 16588)
-- Name: roles; Type: TABLE; Schema: society; Owner: postgres
--

CREATE TABLE society.roles (
    role_id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE society.roles OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16587)
-- Name: roles_role_id_seq; Type: SEQUENCE; Schema: society; Owner: postgres
--

CREATE SEQUENCE society.roles_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE society.roles_role_id_seq OWNER TO postgres;

--
-- TOC entry 3455 (class 0 OID 0)
-- Dependencies: 225
-- Name: roles_role_id_seq; Type: SEQUENCE OWNED BY; Schema: society; Owner: postgres
--

ALTER SEQUENCE society.roles_role_id_seq OWNED BY society.roles.role_id;


--
-- TOC entry 220 (class 1259 OID 16458)
-- Name: society_maintenance_values_year_wise; Type: TABLE; Schema: society; Owner: admin
--

CREATE TABLE society.society_maintenance_values_year_wise (
    recordnum integer NOT NULL,
    financialyear character varying(255),
    flattype character varying(255),
    maintenancevalue double precision NOT NULL
);


ALTER TABLE society.society_maintenance_values_year_wise OWNER TO admin;

--
-- TOC entry 243 (class 1259 OID 24957)
-- Name: societymaintenancepaidhistory; Type: TABLE; Schema: society; Owner: admin
--

CREATE TABLE society.societymaintenancepaidhistory (
    id bigint NOT NULL,
    amount double precision NOT NULL,
    annualmaintenance double precision NOT NULL,
    date date,
    deleted boolean,
    deletedreason character varying(255),
    flatnumber integer NOT NULL,
    flattype character varying(255),
    paymentmethod character varying(255),
    transactionid character varying(255),
    year character varying(255)
);


ALTER TABLE society.societymaintenancepaidhistory OWNER TO admin;

--
-- TOC entry 242 (class 1259 OID 24956)
-- Name: societymaintenancepaidhistory_id_seq; Type: SEQUENCE; Schema: society; Owner: admin
--

CREATE SEQUENCE society.societymaintenancepaidhistory_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE society.societymaintenancepaidhistory_id_seq OWNER TO admin;

--
-- TOC entry 3456 (class 0 OID 0)
-- Dependencies: 242
-- Name: societymaintenancepaidhistory_id_seq; Type: SEQUENCE OWNED BY; Schema: society; Owner: admin
--

ALTER SEQUENCE society.societymaintenancepaidhistory_id_seq OWNED BY society.societymaintenancepaidhistory.id;


--
-- TOC entry 224 (class 1259 OID 16579)
-- Name: users; Type: TABLE; Schema: society; Owner: postgres
--

CREATE TABLE society.users (
    user_id integer NOT NULL,
    username character varying(45) NOT NULL,
    password character varying(64) NOT NULL,
    enabled smallint
);


ALTER TABLE society.users OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 16596)
-- Name: users_roles; Type: TABLE; Schema: society; Owner: postgres
--

CREATE TABLE society.users_roles (
    user_id integer NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE society.users_roles OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16578)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: society; Owner: postgres
--

CREATE SEQUENCE society.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE society.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 3457 (class 0 OID 0)
-- Dependencies: 223
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: society; Owner: postgres
--

ALTER SEQUENCE society.users_user_id_seq OWNED BY society.users.user_id;


--
-- TOC entry 3256 (class 2604 OID 24913)
-- Name: approvalpendingpayments id; Type: DEFAULT; Schema: society; Owner: admin
--

ALTER TABLE ONLY society.approvalpendingpayments ALTER COLUMN id SET DEFAULT nextval('society.approvalpendingpayments_id_seq'::regclass);


--
-- TOC entry 3257 (class 2604 OID 24929)
-- Name: extrachargesentry id; Type: DEFAULT; Schema: society; Owner: admin
--

ALTER TABLE ONLY society.extrachargesentry ALTER COLUMN id SET DEFAULT nextval('society.extrachargesentry_id_seq'::regclass);


--
-- TOC entry 3259 (class 2604 OID 24969)
-- Name: extrachargesentryforallflats id; Type: DEFAULT; Schema: society; Owner: admin
--

ALTER TABLE ONLY society.extrachargesentryforallflats ALTER COLUMN id SET DEFAULT nextval('society.extrachargesentryforallflats_id_seq'::regclass);


--
-- TOC entry 3255 (class 2604 OID 16591)
-- Name: roles role_id; Type: DEFAULT; Schema: society; Owner: postgres
--

ALTER TABLE ONLY society.roles ALTER COLUMN role_id SET DEFAULT nextval('society.roles_role_id_seq'::regclass);


--
-- TOC entry 3258 (class 2604 OID 24960)
-- Name: societymaintenancepaidhistory id; Type: DEFAULT; Schema: society; Owner: admin
--

ALTER TABLE ONLY society.societymaintenancepaidhistory ALTER COLUMN id SET DEFAULT nextval('society.societymaintenancepaidhistory_id_seq'::regclass);


--
-- TOC entry 3254 (class 2604 OID 16582)
-- Name: users user_id; Type: DEFAULT; Schema: society; Owner: postgres
--

ALTER TABLE ONLY society.users ALTER COLUMN user_id SET DEFAULT nextval('society.users_user_id_seq'::regclass);


--
-- TOC entry 3442 (class 0 OID 24948)
-- Dependencies: 241
-- Data for Name: actionedpaymentsdetails; Type: TABLE DATA; Schema: society; Owner: admin
--

INSERT INTO society.actionedpaymentsdetails VALUES (6, 'Approved', 'admin', '06-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2020-08-27', 503, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (11, 'Approved', 'admin', '06-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2021-05-07', 503, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (16, 'Approved', 'admin', '06-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2020-06-11', 204, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (10, 'Rejected', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 14100, '2016-06-01', 704, 'Cash', 'Invalid input', 'Cash');
INSERT INTO society.actionedpaymentsdetails VALUES (1, 'Rejected', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 6475, '2016-11-04', 704, 'Cheque', 'Old Entry', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (2, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 2679, '2017-05-06', 704, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (3, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 7800, '2017-12-16', 704, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (4, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 9420, '2018-06-19', 704, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (5, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2022-07-17', 704, 'UPI', '', 'UPI');
INSERT INTO society.actionedpaymentsdetails VALUES (8, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2023-06-04', 704, 'UPI', '', 'UPI');
INSERT INTO society.actionedpaymentsdetails VALUES (9, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2024-03-28', 704, 'UPI', '', 'UPI');
INSERT INTO society.actionedpaymentsdetails VALUES (7, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2023-03-19', 704, 'UPI', '', 'UPI');
INSERT INTO society.actionedpaymentsdetails VALUES (12, 'Rejected', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2022-03-30', 704, 'UPI', 'wrong entry', 'UPI');
INSERT INTO society.actionedpaymentsdetails VALUES (13, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2021-11-07', 101, 'IMPS', '', 'IMPS');
INSERT INTO society.actionedpaymentsdetails VALUES (14, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2022-08-11', 101, 'IMPS', '', 'IMPS');
INSERT INTO society.actionedpaymentsdetails VALUES (15, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 344440, '2023-03-26', 101, 'IMPS', '', 'IMPS');
INSERT INTO society.actionedpaymentsdetails VALUES (17, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 2590, '2017-11-20', 203, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (18, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 7800, '2018-01-03', 203, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (19, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 9420, '2018-06-19', 203, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (20, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 8610, '2019-06-27', 203, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (21, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 8610, '2020-02-26', 203, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (22, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2020-08-27', 203, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (23, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2021-05-20', 203, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (24, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2024-02-22', 203, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (25, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2022-12-15', 203, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (26, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2023-03-14', 203, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (28, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 5000, '2022-03-04', 203, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (29, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 5000, '2022-03-04', 203, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (27, 'Rejected', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2023-02-05', 203, 'Cheque', 'Transaction mismatch', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (35, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 13265, '2017-06-06', 703, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (36, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 7800, '2017-12-16', 703, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (37, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 9420, '2018-07-06', 703, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (38, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2019-11-15', 703, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (39, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 20716, '2023-03-30', 703, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (40, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 34440, '2023-03-30', 703, 'Cheque', '', 'Cheque');


--
-- TOC entry 3437 (class 0 OID 24910)
-- Dependencies: 236
-- Data for Name: approvalpendingpayments; Type: TABLE DATA; Schema: society; Owner: admin
--

INSERT INTO society.approvalpendingpayments VALUES (6, 17220, '2022-06-30', 704, 'UPI', '208922700753');
INSERT INTO society.approvalpendingpayments VALUES (11, 17220, '2019-11-15', 704, 'Cash', 'NA');
INSERT INTO society.approvalpendingpayments VALUES (16, 17220, '2024-03-31', 101, 'IMPS', '409110831028');
INSERT INTO society.approvalpendingpayments VALUES (30, 19432, '2017-02-09', 702, 'Cheque', '9359');
INSERT INTO society.approvalpendingpayments VALUES (31, 11000, '2018-01-23', 702, 'Cheque', '94726');
INSERT INTO society.approvalpendingpayments VALUES (32, 11440, '2018-06-19', 702, 'Cheque', '94728');
INSERT INTO society.approvalpendingpayments VALUES (33, 5000, '2018-03-02', 702, 'Cheque', '94730');


--
-- TOC entry 3438 (class 0 OID 24918)
-- Dependencies: 237
-- Data for Name: deletedextracharges; Type: TABLE DATA; Schema: society; Owner: admin
--

INSERT INTO society.deletedextracharges VALUES (10, 18090, 'Outstanding of 2016-2017', '', 'Incorrect Information', 703, 'Prior Outstanding');


--
-- TOC entry 3440 (class 0 OID 24926)
-- Dependencies: 239
-- Data for Name: extrachargesentry; Type: TABLE DATA; Schema: society; Owner: admin
--

INSERT INTO society.extrachargesentry VALUES (1, 10465, 'Outstanding of 2016-2017', 704, 'Prior Outstanding', NULL);
INSERT INTO society.extrachargesentry VALUES (2, 2590, 'Outstanding of 2016-2017', 404, 'Prior Outstanding', NULL);
INSERT INTO society.extrachargesentry VALUES (3, 2590, 'Outstanding of 2016-2017', 301, 'Prior Outstanding', NULL);
INSERT INTO society.extrachargesentry VALUES (4, 2590, 'Outstanding of 2016-2017', 602, 'Prior Outstanding', NULL);
INSERT INTO society.extrachargesentry VALUES (7, 2590, 'Outstanding of 2016-2017', 101, 'Prior Outstanding', NULL);
INSERT INTO society.extrachargesentry VALUES (8, 2590, 'Outstanding of 2016-2017', 203, 'Prior Outstanding', NULL);
INSERT INTO society.extrachargesentry VALUES (9, 3426, 'Outstanding of 2016-2017', 702, 'Prior Outstanding', NULL);
INSERT INTO society.extrachargesentry VALUES (11, 5000, 'Borewell Contribution ', 203, 'Common Contribution', NULL);


--
-- TOC entry 3446 (class 0 OID 24966)
-- Dependencies: 245
-- Data for Name: extrachargesentryforallflats; Type: TABLE DATA; Schema: society; Owner: admin
--



--
-- TOC entry 3429 (class 0 OID 16448)
-- Dependencies: 219
-- Data for Name: flat_type_flat_number_map; Type: TABLE DATA; Schema: society; Owner: admin
--

INSERT INTO society.flat_type_flat_number_map VALUES (101, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (102, 22440, '2BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (103, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (104, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (201, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (202, 22440, '2BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (203, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (204, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (301, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (302, 22440, '2BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (303, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (304, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (401, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (402, 22440, '2BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (403, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (404, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (501, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (502, 18120, '1.5BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (503, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (504, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (601, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (602, 22440, '2BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (603, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (604, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (701, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (702, 22440, '2BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (704, 17220, '1BHK');
INSERT INTO society.flat_type_flat_number_map VALUES (703, 17220, '1BHK');


--
-- TOC entry 3441 (class 0 OID 24934)
-- Dependencies: 240
-- Data for Name: maintenancemasterentry; Type: TABLE DATA; Schema: society; Owner: admin
--

INSERT INTO society.maintenancemasterentry VALUES (304, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (701, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (504, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (103, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (502, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (401, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (601, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (202, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (302, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (204, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (104, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (604, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (303, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (201, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (402, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (102, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (603, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (503, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (501, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (403, 5000, NULL, NULL, 0, 5000);
INSERT INTO society.maintenancemasterentry VALUES (101, 5000, '2018-19', '2023-03-26', 378880, -236120);
INSERT INTO society.maintenancemasterentry VALUES (703, -13090, '2019-20', '2023-03-30', 102861, 63569);
INSERT INTO society.maintenancemasterentry VALUES (602, 8426, NULL, NULL, 0, 8426);
INSERT INTO society.maintenancemasterentry VALUES (404, 7590, NULL, NULL, 0, 7590);
INSERT INTO society.maintenancemasterentry VALUES (702, 8426, NULL, NULL, 0, 8426);
INSERT INTO society.maintenancemasterentry VALUES (704, 5000, '2020-21', '2023-03-19', 88779, 53981);
INSERT INTO society.maintenancemasterentry VALUES (301, 7590, NULL, NULL, 0, 7590);
INSERT INTO society.maintenancemasterentry VALUES (203, 5000, '2023-24', '2022-03-04', 128130, 14630);


--
-- TOC entry 3434 (class 0 OID 16588)
-- Dependencies: 226
-- Data for Name: roles; Type: TABLE DATA; Schema: society; Owner: postgres
--

INSERT INTO society.roles VALUES (1, 'ADMIN');
INSERT INTO society.roles VALUES (2, 'USER');


--
-- TOC entry 3430 (class 0 OID 16458)
-- Dependencies: 220
-- Data for Name: society_maintenance_values_year_wise; Type: TABLE DATA; Schema: society; Owner: admin
--

INSERT INTO society.society_maintenance_values_year_wise VALUES (4, '2017-18', '1BHK', 17220);
INSERT INTO society.society_maintenance_values_year_wise VALUES (5, '2017-18', '1.5BHK', 18120);
INSERT INTO society.society_maintenance_values_year_wise VALUES (6, '2017-18', '2BHK', 22440);
INSERT INTO society.society_maintenance_values_year_wise VALUES (7, '2018-19', '1BHK', 17220);
INSERT INTO society.society_maintenance_values_year_wise VALUES (8, '2018-19', '1.5BHK', 18120);
INSERT INTO society.society_maintenance_values_year_wise VALUES (9, '2018-19', '2BHK', 22440);
INSERT INTO society.society_maintenance_values_year_wise VALUES (10, '2019-20', '1BHK', 17220);
INSERT INTO society.society_maintenance_values_year_wise VALUES (11, '2019-20', '1.5BHK', 18120);
INSERT INTO society.society_maintenance_values_year_wise VALUES (12, '2019-20', '2BHK', 22440);
INSERT INTO society.society_maintenance_values_year_wise VALUES (13, '2020-21', '1BHK', 17220);
INSERT INTO society.society_maintenance_values_year_wise VALUES (14, '2020-21', '1.5BHK', 18120);
INSERT INTO society.society_maintenance_values_year_wise VALUES (15, '2020-21', '2BHK', 22440);
INSERT INTO society.society_maintenance_values_year_wise VALUES (16, '2021-22', '1BHK', 17220);
INSERT INTO society.society_maintenance_values_year_wise VALUES (17, '2021-22', '1.5BHK', 18120);
INSERT INTO society.society_maintenance_values_year_wise VALUES (18, '2021-22', '2BHK', 22440);
INSERT INTO society.society_maintenance_values_year_wise VALUES (19, '2022-23', '1BHK', 17220);
INSERT INTO society.society_maintenance_values_year_wise VALUES (20, '2022-23', '1.5BHK', 18120);
INSERT INTO society.society_maintenance_values_year_wise VALUES (21, '2022-23', '2BHK', 22440);
INSERT INTO society.society_maintenance_values_year_wise VALUES (22, '2023-24', '1BHK', 17220);
INSERT INTO society.society_maintenance_values_year_wise VALUES (23, '2023-24', '1.5BHK', 18120);
INSERT INTO society.society_maintenance_values_year_wise VALUES (24, '2023-24', '2BHK', 22440);
INSERT INTO society.society_maintenance_values_year_wise VALUES (25, '2024-25', '1BHK', 17220);
INSERT INTO society.society_maintenance_values_year_wise VALUES (26, '2024-25', '1.5BHK', 18120);
INSERT INTO society.society_maintenance_values_year_wise VALUES (27, '2024-25', '2BHK', 22440);


--
-- TOC entry 3444 (class 0 OID 24957)
-- Dependencies: 243
-- Data for Name: societymaintenancepaidhistory; Type: TABLE DATA; Schema: society; Owner: admin
--

INSERT INTO society.societymaintenancepaidhistory VALUES (1, 2679, 17220, '2017-05-06', false, NULL, 704, '1BHK', 'Cheque', '390051', '2017-18');
INSERT INTO society.societymaintenancepaidhistory VALUES (2, 7800, 17220, '2017-12-16', false, NULL, 704, '1BHK', 'Cheque', '629057', '2017-18');
INSERT INTO society.societymaintenancepaidhistory VALUES (3, 9420, 17220, '2018-06-19', false, NULL, 704, '1BHK', 'Cheque', '629058', '2018-19');
INSERT INTO society.societymaintenancepaidhistory VALUES (4, 17220, 17220, '2022-07-17', false, NULL, 704, '1BHK', 'UPI', '219812497594', '2022-23');
INSERT INTO society.societymaintenancepaidhistory VALUES (5, 17220, 17220, '2023-06-04', false, NULL, 704, '1BHK', 'UPI', '315568069613', '2023-24');
INSERT INTO society.societymaintenancepaidhistory VALUES (6, 17220, 17220, '2024-03-28', false, NULL, 704, '1BHK', 'UPI', '408880599685', '2023-24');
INSERT INTO society.societymaintenancepaidhistory VALUES (7, 17220, 17220, '2023-03-19', false, NULL, 704, '1BHK', 'UPI', '307842811037', '2022-23');
INSERT INTO society.societymaintenancepaidhistory VALUES (8, 17220, 17220, '2021-11-07', false, NULL, 101, '1BHK', 'IMPS', '13111294221', '2021-22');
INSERT INTO society.societymaintenancepaidhistory VALUES (9, 17220, 17220, '2022-08-11', false, NULL, 101, '1BHK', 'IMPS', '222311920108', '2022-23');
INSERT INTO society.societymaintenancepaidhistory VALUES (10, 344440, 17220, '2023-03-26', false, NULL, 101, '1BHK', 'IMPS', '308510623856', '2022-23');
INSERT INTO society.societymaintenancepaidhistory VALUES (11, 2590, 17220, '2017-11-20', false, NULL, 203, '1BHK', 'Cheque', '100049', '2017-18');
INSERT INTO society.societymaintenancepaidhistory VALUES (12, 7800, 17220, '2018-01-03', false, NULL, 203, '1BHK', 'Cheque', '100052', '2017-18');
INSERT INTO society.societymaintenancepaidhistory VALUES (13, 9420, 17220, '2018-06-19', false, NULL, 203, '1BHK', 'Cheque', '100054', '2018-19');
INSERT INTO society.societymaintenancepaidhistory VALUES (14, 8610, 17220, '2019-06-27', false, NULL, 203, '1BHK', 'Cheque', '100080', '2019-20');
INSERT INTO society.societymaintenancepaidhistory VALUES (15, 8610, 17220, '2020-02-26', false, NULL, 203, '1BHK', 'Cheque', '100089', '2019-20');
INSERT INTO society.societymaintenancepaidhistory VALUES (16, 17220, 17220, '2020-08-27', false, NULL, 203, '1BHK', 'Cheque', '100101', '2020-21');
INSERT INTO society.societymaintenancepaidhistory VALUES (17, 17220, 17220, '2021-05-20', false, NULL, 203, '1BHK', 'Cheque', '100114', '2021-22');
INSERT INTO society.societymaintenancepaidhistory VALUES (18, 17220, 17220, '2024-02-22', false, NULL, 203, '1BHK', 'Cheque', '100164', '2023-24');
INSERT INTO society.societymaintenancepaidhistory VALUES (19, 17220, 17220, '2022-12-15', false, NULL, 203, '1BHK', 'Cheque', '100145', '2022-23');
INSERT INTO society.societymaintenancepaidhistory VALUES (20, 17220, 17220, '2023-03-14', false, NULL, 203, '1BHK', 'Cheque', '100149', '2022-23');
INSERT INTO society.societymaintenancepaidhistory VALUES (21, 5000, 17220, '2022-03-04', true, 'Incorrect Information', 203, '1BHK', 'Cheque', '100149', '2021-22');
INSERT INTO society.societymaintenancepaidhistory VALUES (22, 5000, 17220, '2022-03-04', false, NULL, 203, '1BHK', 'Cheque', '100125', '2021-22');
INSERT INTO society.societymaintenancepaidhistory VALUES (23, 13265, 17220, '2017-06-06', false, NULL, 703, '2BHK', 'Cheque', '49695', '2017-18');
INSERT INTO society.societymaintenancepaidhistory VALUES (24, 7800, 17220, '2017-12-16', false, NULL, 703, '2BHK', 'Cheque', '78244', '2017-18');
INSERT INTO society.societymaintenancepaidhistory VALUES (25, 9420, 17220, '2018-07-06', false, NULL, 703, '2BHK', 'Cheque', '78259', '2018-19');
INSERT INTO society.societymaintenancepaidhistory VALUES (26, 17220, 17220, '2019-11-15', false, NULL, 703, '2BHK', 'Cheque', '123986', '2019-20');
INSERT INTO society.societymaintenancepaidhistory VALUES (27, 20716, 17220, '2023-03-30', false, NULL, 703, '2BHK', 'Cheque', '165851', '2022-23');
INSERT INTO society.societymaintenancepaidhistory VALUES (28, 34440, 17220, '2023-03-30', false, NULL, 703, '2BHK', 'Cheque', '350451', '2022-23');


--
-- TOC entry 3432 (class 0 OID 16579)
-- Dependencies: 224
-- Data for Name: users; Type: TABLE DATA; Schema: society; Owner: postgres
--

INSERT INTO society.users VALUES (1, 'admin', '$2b$12$fzFPKMEiDGduQZplzBelaOrKKOxN0IrDvXnKPId0ZCmdgWYjEQkrq', 1);
INSERT INTO society.users VALUES (2, 'member', '$2a$12$O2LewonIRTGj0oGeQzoCnuNYJhZEHYgSSRRw1TsCIxvPwaNbKCAji', 1);


--
-- TOC entry 3435 (class 0 OID 16596)
-- Dependencies: 227
-- Data for Name: users_roles; Type: TABLE DATA; Schema: society; Owner: postgres
--

INSERT INTO society.users_roles VALUES (1, 1);
INSERT INTO society.users_roles VALUES (2, 2);


--
-- TOC entry 3458 (class 0 OID 0)
-- Dependencies: 235
-- Name: approvalpendingpayments_id_seq; Type: SEQUENCE SET; Schema: society; Owner: admin
--

SELECT pg_catalog.setval('society.approvalpendingpayments_id_seq', 40, true);


--
-- TOC entry 3459 (class 0 OID 0)
-- Dependencies: 238
-- Name: extrachargesentry_id_seq; Type: SEQUENCE SET; Schema: society; Owner: admin
--

SELECT pg_catalog.setval('society.extrachargesentry_id_seq', 11, true);


--
-- TOC entry 3460 (class 0 OID 0)
-- Dependencies: 244
-- Name: extrachargesentryforallflats_id_seq; Type: SEQUENCE SET; Schema: society; Owner: admin
--

SELECT pg_catalog.setval('society.extrachargesentryforallflats_id_seq', 1, false);


--
-- TOC entry 3461 (class 0 OID 0)
-- Dependencies: 225
-- Name: roles_role_id_seq; Type: SEQUENCE SET; Schema: society; Owner: postgres
--

SELECT pg_catalog.setval('society.roles_role_id_seq', 1, false);


--
-- TOC entry 3462 (class 0 OID 0)
-- Dependencies: 242
-- Name: societymaintenancepaidhistory_id_seq; Type: SEQUENCE SET; Schema: society; Owner: admin
--

SELECT pg_catalog.setval('society.societymaintenancepaidhistory_id_seq', 28, true);


--
-- TOC entry 3463 (class 0 OID 0)
-- Dependencies: 223
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: society; Owner: postgres
--

SELECT pg_catalog.setval('society.users_user_id_seq', 2, true);


--
-- TOC entry 3283 (class 2606 OID 24954)
-- Name: actionedpaymentsdetails actionedpaymentsdetails_pkey; Type: CONSTRAINT; Schema: society; Owner: admin
--

ALTER TABLE ONLY society.actionedpaymentsdetails
    ADD CONSTRAINT actionedpaymentsdetails_pkey PRIMARY KEY (id);


--
-- TOC entry 3275 (class 2606 OID 24917)
-- Name: approvalpendingpayments approvalpendingpayments_pkey; Type: CONSTRAINT; Schema: society; Owner: admin
--

ALTER TABLE ONLY society.approvalpendingpayments
    ADD CONSTRAINT approvalpendingpayments_pkey PRIMARY KEY (id);


--
-- TOC entry 3277 (class 2606 OID 24924)
-- Name: deletedextracharges deletedextracharges_pkey; Type: CONSTRAINT; Schema: society; Owner: admin
--

ALTER TABLE ONLY society.deletedextracharges
    ADD CONSTRAINT deletedextracharges_pkey PRIMARY KEY (id);


--
-- TOC entry 3279 (class 2606 OID 24933)
-- Name: extrachargesentry extrachargesentry_pkey; Type: CONSTRAINT; Schema: society; Owner: admin
--

ALTER TABLE ONLY society.extrachargesentry
    ADD CONSTRAINT extrachargesentry_pkey PRIMARY KEY (id);


--
-- TOC entry 3287 (class 2606 OID 24973)
-- Name: extrachargesentryforallflats extrachargesentryforallflats_pkey; Type: CONSTRAINT; Schema: society; Owner: admin
--

ALTER TABLE ONLY society.extrachargesentryforallflats
    ADD CONSTRAINT extrachargesentryforallflats_pkey PRIMARY KEY (id);


--
-- TOC entry 3261 (class 2606 OID 16452)
-- Name: flat_type_flat_number_map flat_type_flat_number_map_pkey; Type: CONSTRAINT; Schema: society; Owner: admin
--

ALTER TABLE ONLY society.flat_type_flat_number_map
    ADD CONSTRAINT flat_type_flat_number_map_pkey PRIMARY KEY (flatnumber);


--
-- TOC entry 3281 (class 2606 OID 24938)
-- Name: maintenancemasterentry maintenancemasterentry_pkey; Type: CONSTRAINT; Schema: society; Owner: admin
--

ALTER TABLE ONLY society.maintenancemasterentry
    ADD CONSTRAINT maintenancemasterentry_pkey PRIMARY KEY (flatnumber);


--
-- TOC entry 3269 (class 2606 OID 16595)
-- Name: roles roles_name_key; Type: CONSTRAINT; Schema: society; Owner: postgres
--

ALTER TABLE ONLY society.roles
    ADD CONSTRAINT roles_name_key UNIQUE (name);


--
-- TOC entry 3271 (class 2606 OID 16593)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: society; Owner: postgres
--

ALTER TABLE ONLY society.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (role_id);


--
-- TOC entry 3263 (class 2606 OID 16464)
-- Name: society_maintenance_values_year_wise society_maintenance_values_year_wise_pkey; Type: CONSTRAINT; Schema: society; Owner: admin
--

ALTER TABLE ONLY society.society_maintenance_values_year_wise
    ADD CONSTRAINT society_maintenance_values_year_wise_pkey PRIMARY KEY (recordnum);


--
-- TOC entry 3285 (class 2606 OID 24964)
-- Name: societymaintenancepaidhistory societymaintenancepaidhistory_pkey; Type: CONSTRAINT; Schema: society; Owner: admin
--

ALTER TABLE ONLY society.societymaintenancepaidhistory
    ADD CONSTRAINT societymaintenancepaidhistory_pkey PRIMARY KEY (id);


--
-- TOC entry 3265 (class 2606 OID 16584)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: society; Owner: postgres
--

ALTER TABLE ONLY society.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 3273 (class 2606 OID 16600)
-- Name: users_roles users_roles_pkey; Type: CONSTRAINT; Schema: society; Owner: postgres
--

ALTER TABLE ONLY society.users_roles
    ADD CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 3267 (class 2606 OID 16586)
-- Name: users users_username_key; Type: CONSTRAINT; Schema: society; Owner: postgres
--

ALTER TABLE ONLY society.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- TOC entry 3288 (class 2606 OID 16606)
-- Name: users_roles users_roles_role_id_fkey; Type: FK CONSTRAINT; Schema: society; Owner: postgres
--

ALTER TABLE ONLY society.users_roles
    ADD CONSTRAINT users_roles_role_id_fkey FOREIGN KEY (role_id) REFERENCES society.roles(role_id);


--
-- TOC entry 3289 (class 2606 OID 16601)
-- Name: users_roles users_roles_user_id_fkey; Type: FK CONSTRAINT; Schema: society; Owner: postgres
--

ALTER TABLE ONLY society.users_roles
    ADD CONSTRAINT users_roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES society.users(user_id);


-- Completed on 2024-08-17 19:11:31

--
-- PostgreSQL database dump complete
--

