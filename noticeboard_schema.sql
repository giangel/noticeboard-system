--
-- PostgreSQL database dump
--

\restrict bfDUnLgQx64j5EwmJ2YT9pAdQhrgjbGZZ5x4kEd171PXM8PPhjVG6FkNkaRumpJ

-- Dumped from database version 18.1
-- Dumped by pg_dump version 18.1

-- Started on 2026-07-21 15:00:30

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 226 (class 1259 OID 27088)
-- Name: administrators; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.administrators (
    admin_id integer NOT NULL,
    user_id integer NOT NULL,
    staff_id character varying(30) NOT NULL,
    designation character varying(50)
);


ALTER TABLE public.administrators OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 27087)
-- Name: administrators_admin_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.administrators_admin_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.administrators_admin_id_seq OWNER TO postgres;

--
-- TOC entry 5103 (class 0 OID 0)
-- Dependencies: 225
-- Name: administrators_admin_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.administrators_admin_id_seq OWNED BY public.administrators.admin_id;


--
-- TOC entry 236 (class 1259 OID 27203)
-- Name: audit_logs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.audit_logs (
    log_id integer NOT NULL,
    user_id integer NOT NULL,
    action character varying(100) NOT NULL,
    action_details character varying(255),
    action_time timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.audit_logs OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 27202)
-- Name: audit_logs_log_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.audit_logs_log_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.audit_logs_log_id_seq OWNER TO postgres;

--
-- TOC entry 5104 (class 0 OID 0)
-- Dependencies: 235
-- Name: audit_logs_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.audit_logs_log_id_seq OWNED BY public.audit_logs.log_id;


--
-- TOC entry 234 (class 1259 OID 27179)
-- Name: notice_bookmarks; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notice_bookmarks (
    bookmark_id integer NOT NULL,
    notice_id integer NOT NULL,
    student_id integer NOT NULL,
    bookmarked_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.notice_bookmarks OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 27178)
-- Name: notice_bookmarks_bookmark_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.notice_bookmarks_bookmark_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.notice_bookmarks_bookmark_id_seq OWNER TO postgres;

--
-- TOC entry 5105 (class 0 OID 0)
-- Dependencies: 233
-- Name: notice_bookmarks_bookmark_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.notice_bookmarks_bookmark_id_seq OWNED BY public.notice_bookmarks.bookmark_id;


--
-- TOC entry 228 (class 1259 OID 27107)
-- Name: notice_categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notice_categories (
    category_id integer NOT NULL,
    category_name character varying(50) NOT NULL,
    category_description character varying(255)
);


ALTER TABLE public.notice_categories OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 27106)
-- Name: notice_categories_category_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.notice_categories_category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.notice_categories_category_id_seq OWNER TO postgres;

--
-- TOC entry 5106 (class 0 OID 0)
-- Dependencies: 227
-- Name: notice_categories_category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.notice_categories_category_id_seq OWNED BY public.notice_categories.category_id;


--
-- TOC entry 232 (class 1259 OID 27155)
-- Name: notice_views; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notice_views (
    view_id integer NOT NULL,
    notice_id integer NOT NULL,
    student_id integer NOT NULL,
    viewed_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.notice_views OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 27154)
-- Name: notice_views_view_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.notice_views_view_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.notice_views_view_id_seq OWNER TO postgres;

--
-- TOC entry 5107 (class 0 OID 0)
-- Dependencies: 231
-- Name: notice_views_view_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.notice_views_view_id_seq OWNED BY public.notice_views.view_id;


--
-- TOC entry 230 (class 1259 OID 27118)
-- Name: notices; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notices (
    notice_id integer NOT NULL,
    title character varying(200) NOT NULL,
    content text NOT NULL,
    category_id integer NOT NULL,
    urgency_level character varying(20) DEFAULT 'NORMAL'::character varying NOT NULL,
    target_audience character varying(20) DEFAULT 'ALL'::character varying NOT NULL,
    academic_level character varying(20),
    class_group character varying(30),
    created_by integer NOT NULL,
    date_created timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    date_published timestamp without time zone,
    expiry_date timestamp without time zone,
    status character varying(20) DEFAULT 'DRAFT'::character varying NOT NULL,
    view_count integer DEFAULT 0 NOT NULL,
    CONSTRAINT chk_notice_status CHECK (((status)::text = ANY ((ARRAY['DRAFT'::character varying, 'PUBLISHED'::character varying, 'ARCHIVED'::character varying, 'EXPIRED'::character varying])::text[]))),
    CONSTRAINT chk_target_audience CHECK (((target_audience)::text = ANY ((ARRAY['ALL'::character varying, 'LEVEL'::character varying, 'CLASS'::character varying])::text[]))),
    CONSTRAINT chk_urgency_level CHECK (((urgency_level)::text = ANY ((ARRAY['NORMAL'::character varying, 'IMPORTANT'::character varying, 'URGENT'::character varying])::text[])))
);


ALTER TABLE public.notices OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 27117)
-- Name: notices_notice_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.notices_notice_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.notices_notice_id_seq OWNER TO postgres;

--
-- TOC entry 5108 (class 0 OID 0)
-- Dependencies: 229
-- Name: notices_notice_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.notices_notice_id_seq OWNED BY public.notices.notice_id;


--
-- TOC entry 220 (class 1259 OID 27028)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    role_id integer NOT NULL,
    role_name character varying(20) NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 27027)
-- Name: roles_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.roles_role_id_seq OWNER TO postgres;

--
-- TOC entry 5109 (class 0 OID 0)
-- Dependencies: 219
-- Name: roles_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_role_id_seq OWNED BY public.roles.role_id;


--
-- TOC entry 224 (class 1259 OID 27068)
-- Name: students; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.students (
    student_id integer NOT NULL,
    user_id integer NOT NULL,
    matric_number character varying(30) NOT NULL,
    academic_level character varying(20) NOT NULL,
    class_group character varying(30)
);


ALTER TABLE public.students OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 27067)
-- Name: students_student_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.students_student_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.students_student_id_seq OWNER TO postgres;

--
-- TOC entry 5110 (class 0 OID 0)
-- Dependencies: 223
-- Name: students_student_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.students_student_id_seq OWNED BY public.students.student_id;


--
-- TOC entry 222 (class 1259 OID 27039)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    username character varying(50) NOT NULL,
    email character varying(100) NOT NULL,
    password_hash character varying(255) NOT NULL,
    full_name character varying(100) NOT NULL,
    role_id integer NOT NULL,
    account_status character varying(20) DEFAULT 'ACTIVE'::character varying NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT chk_account_status CHECK (((account_status)::text = ANY ((ARRAY['ACTIVE'::character varying, 'INACTIVE'::character varying])::text[])))
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 27038)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 5111 (class 0 OID 0)
-- Dependencies: 221
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- TOC entry 4854 (class 2604 OID 27091)
-- Name: administrators admin_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administrators ALTER COLUMN admin_id SET DEFAULT nextval('public.administrators_admin_id_seq'::regclass);


--
-- TOC entry 4866 (class 2604 OID 27206)
-- Name: audit_logs log_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.audit_logs ALTER COLUMN log_id SET DEFAULT nextval('public.audit_logs_log_id_seq'::regclass);


--
-- TOC entry 4864 (class 2604 OID 27182)
-- Name: notice_bookmarks bookmark_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notice_bookmarks ALTER COLUMN bookmark_id SET DEFAULT nextval('public.notice_bookmarks_bookmark_id_seq'::regclass);


--
-- TOC entry 4855 (class 2604 OID 27110)
-- Name: notice_categories category_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notice_categories ALTER COLUMN category_id SET DEFAULT nextval('public.notice_categories_category_id_seq'::regclass);


--
-- TOC entry 4862 (class 2604 OID 27158)
-- Name: notice_views view_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notice_views ALTER COLUMN view_id SET DEFAULT nextval('public.notice_views_view_id_seq'::regclass);


--
-- TOC entry 4856 (class 2604 OID 27121)
-- Name: notices notice_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notices ALTER COLUMN notice_id SET DEFAULT nextval('public.notices_notice_id_seq'::regclass);


--
-- TOC entry 4849 (class 2604 OID 27031)
-- Name: roles role_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN role_id SET DEFAULT nextval('public.roles_role_id_seq'::regclass);


--
-- TOC entry 4853 (class 2604 OID 27071)
-- Name: students student_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.students ALTER COLUMN student_id SET DEFAULT nextval('public.students_student_id_seq'::regclass);


--
-- TOC entry 4850 (class 2604 OID 27042)
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- TOC entry 5087 (class 0 OID 27088)
-- Dependencies: 226
-- Data for Name: administrators; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.administrators (admin_id, user_id, staff_id, designation) FROM stdin;
1	1	STAFF001	Head of Department
\.


--
-- TOC entry 5097 (class 0 OID 27203)
-- Dependencies: 236
-- Data for Name: audit_logs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.audit_logs (log_id, user_id, action, action_details, action_time) FROM stdin;
1	1	UPDATE_USER_STATUS	Changed status of user ID 3 to INACTIVE	2026-07-21 14:08:22.939667
2	1	UPDATE_USER_STATUS	Changed status of user ID 2 to INACTIVE	2026-07-21 14:08:26.638162
3	1	CREATE_NOTICE	Created notice titled: Resumption date	2026-07-21 14:11:21.364304
\.


--
-- TOC entry 5095 (class 0 OID 27179)
-- Dependencies: 234
-- Data for Name: notice_bookmarks; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.notice_bookmarks (bookmark_id, notice_id, student_id, bookmarked_at) FROM stdin;
1	1	3	2026-07-21 13:09:16.304614
\.


--
-- TOC entry 5089 (class 0 OID 27107)
-- Dependencies: 228
-- Data for Name: notice_categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.notice_categories (category_id, category_name, category_description) FROM stdin;
1	General Announcement	General departmental announcements
2	Examination	Examination timetables and related notices
3	Lecture	Lecture schedule changes and updates
4	Seminar	Seminar and workshop announcements
5	Result	Examination and continuous assessment results
6	Registration	Course and semester registration notices
7	Meeting	Departmental meeting notices
8	Departmental Directive	Official directives from the department
9	Internship	Industrial training and internship notices
10	Scholarship	Scholarship and financial aid opportunities
11	Urgent Notice	Time critical notices requiring immediate attention
12	Other	Notices that do not fall under a specific category
\.


--
-- TOC entry 5093 (class 0 OID 27155)
-- Dependencies: 232
-- Data for Name: notice_views; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.notice_views (view_id, notice_id, student_id, viewed_at) FROM stdin;
1	3	3	2026-07-21 12:22:47.372
3	4	3	2026-07-21 12:29:41.997
2	1	3	2026-07-21 13:30:42.493
\.


--
-- TOC entry 5091 (class 0 OID 27118)
-- Dependencies: 230
-- Data for Name: notices; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.notices (notice_id, title, content, category_id, urgency_level, target_audience, academic_level, class_group, created_by, date_created, date_published, expiry_date, status, view_count) FROM stdin;
2	Change of Lecture Venue for CSC 201	Please be informed that the lecture venue for CSC 201 has been changed to Lecture Theatre 2 with immediate effect.	3	IMPORTANT	LEVEL	ND2	\N	1	2026-07-21 11:26:37.074091	2026-07-21 11:26:37.074091	2026-07-28 11:26:37.074091	PUBLISHED	0
5	Draft Notice on Industrial Training Placement	This is a draft notice concerning industrial training placement for ND2 students. Content is still being finalized.	9	NORMAL	LEVEL	ND2	\N	1	2026-07-21 11:26:37.074091	\N	\N	DRAFT	0
3	Departmental Seminar on Software Engineering Practices	The department invites all students to a seminar on modern software engineering practices holding in the main auditorium.	4	NORMAL	ALL	\N	\N	1	2026-07-21 11:26:37.074091	2026-07-21 11:26:37.074091	2026-07-31 11:26:37.074091	PUBLISHED	1
1	First Semester Examination Timetable Released	The examination timetable for the first semester has been released. All students are to check the department notice board for their examination dates and venues.	2	URGENT	ALL	\N	\N	1	2026-07-21 11:26:37.074091	2026-07-21 11:26:37.074091	2026-08-04 11:26:37.074091	PUBLISHED	1
4	Second Semester Registration Now Open	Second semester course registration is now open on the student portal. All students are advised to complete registration before the deadline.	6	IMPORTANT	ALL	\N	\N	1	2026-07-21 11:26:37.074091	2026-07-21 11:26:37.074091	2026-08-11 11:26:37.074091	PUBLISHED	1
6	Resumption date	The school resumes on 27th July, 2026	1	IMPORTANT	ALL	\N	\N	1	2026-07-21 14:11:21.297893	2026-07-21 14:11:21.235	2026-08-09 23:59:00	PUBLISHED	0
\.


--
-- TOC entry 5081 (class 0 OID 27028)
-- Dependencies: 220
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (role_id, role_name) FROM stdin;
1	ADMIN
2	STUDENT
\.


--
-- TOC entry 5085 (class 0 OID 27068)
-- Dependencies: 224
-- Data for Name: students; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.students (student_id, user_id, matric_number, academic_level, class_group) FROM stdin;
3	4	123456	ND1	
\.


--
-- TOC entry 5083 (class 0 OID 27039)
-- Dependencies: 222
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (user_id, username, email, password_hash, full_name, role_id, account_status, created_at) FROM stdin;
4	gbemiga	gbemiga@gmail.com	BVkbywgENPVUJYvNEre5kQ==:sxLmW1YmbFeURYAyEb8pkdeTtvZa+gBdvFacbh1oVp0=	gbemiga akinola	2	ACTIVE	2026-07-21 12:20:02.697407
1	admin	admin@aopoly.edu.ng	4l7//ejMRtBKKlAvtVPbUA==:qFp6L7LZF7AAM3az7q6pmxq/F50JbV1DOpq/NKxB0Ik=	Departmental Administrator	1	ACTIVE	2026-07-21 11:26:37.074091
\.


--
-- TOC entry 5112 (class 0 OID 0)
-- Dependencies: 225
-- Name: administrators_admin_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.administrators_admin_id_seq', 1, true);


--
-- TOC entry 5113 (class 0 OID 0)
-- Dependencies: 235
-- Name: audit_logs_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.audit_logs_log_id_seq', 3, true);


--
-- TOC entry 5114 (class 0 OID 0)
-- Dependencies: 233
-- Name: notice_bookmarks_bookmark_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.notice_bookmarks_bookmark_id_seq', 1, true);


--
-- TOC entry 5115 (class 0 OID 0)
-- Dependencies: 227
-- Name: notice_categories_category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.notice_categories_category_id_seq', 12, true);


--
-- TOC entry 5116 (class 0 OID 0)
-- Dependencies: 231
-- Name: notice_views_view_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.notice_views_view_id_seq', 7, true);


--
-- TOC entry 5117 (class 0 OID 0)
-- Dependencies: 229
-- Name: notices_notice_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.notices_notice_id_seq', 6, true);


--
-- TOC entry 5118 (class 0 OID 0)
-- Dependencies: 219
-- Name: roles_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_role_id_seq', 2, true);


--
-- TOC entry 5119 (class 0 OID 0)
-- Dependencies: 223
-- Name: students_student_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.students_student_id_seq', 3, true);


--
-- TOC entry 5120 (class 0 OID 0)
-- Dependencies: 221
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 4, true);


--
-- TOC entry 4893 (class 2606 OID 27096)
-- Name: administrators administrators_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administrators
    ADD CONSTRAINT administrators_pkey PRIMARY KEY (admin_id);


--
-- TOC entry 4895 (class 2606 OID 27100)
-- Name: administrators administrators_staff_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administrators
    ADD CONSTRAINT administrators_staff_id_key UNIQUE (staff_id);


--
-- TOC entry 4897 (class 2606 OID 27098)
-- Name: administrators administrators_user_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administrators
    ADD CONSTRAINT administrators_user_id_key UNIQUE (user_id);


--
-- TOC entry 4921 (class 2606 OID 27213)
-- Name: audit_logs audit_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.audit_logs
    ADD CONSTRAINT audit_logs_pkey PRIMARY KEY (log_id);


--
-- TOC entry 4917 (class 2606 OID 27189)
-- Name: notice_bookmarks notice_bookmarks_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notice_bookmarks
    ADD CONSTRAINT notice_bookmarks_pkey PRIMARY KEY (bookmark_id);


--
-- TOC entry 4899 (class 2606 OID 27116)
-- Name: notice_categories notice_categories_category_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notice_categories
    ADD CONSTRAINT notice_categories_category_name_key UNIQUE (category_name);


--
-- TOC entry 4901 (class 2606 OID 27114)
-- Name: notice_categories notice_categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notice_categories
    ADD CONSTRAINT notice_categories_pkey PRIMARY KEY (category_id);


--
-- TOC entry 4912 (class 2606 OID 27165)
-- Name: notice_views notice_views_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notice_views
    ADD CONSTRAINT notice_views_pkey PRIMARY KEY (view_id);


--
-- TOC entry 4909 (class 2606 OID 27143)
-- Name: notices notices_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notices
    ADD CONSTRAINT notices_pkey PRIMARY KEY (notice_id);


--
-- TOC entry 4873 (class 2606 OID 27035)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (role_id);


--
-- TOC entry 4875 (class 2606 OID 27037)
-- Name: roles roles_role_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_role_name_key UNIQUE (role_name);


--
-- TOC entry 4887 (class 2606 OID 27081)
-- Name: students students_matric_number_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_matric_number_key UNIQUE (matric_number);


--
-- TOC entry 4889 (class 2606 OID 27077)
-- Name: students students_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_pkey PRIMARY KEY (student_id);


--
-- TOC entry 4891 (class 2606 OID 27079)
-- Name: students students_user_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_user_id_key UNIQUE (user_id);


--
-- TOC entry 4919 (class 2606 OID 27191)
-- Name: notice_bookmarks uq_notice_student_bookmark; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notice_bookmarks
    ADD CONSTRAINT uq_notice_student_bookmark UNIQUE (notice_id, student_id);


--
-- TOC entry 4914 (class 2606 OID 27167)
-- Name: notice_views uq_notice_student_view; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notice_views
    ADD CONSTRAINT uq_notice_student_view UNIQUE (notice_id, student_id);


--
-- TOC entry 4879 (class 2606 OID 27061)
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 4881 (class 2606 OID 27057)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 4883 (class 2606 OID 27059)
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- TOC entry 4922 (class 1259 OID 27231)
-- Name: idx_audit_action_time; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_audit_action_time ON public.audit_logs USING btree (action_time);


--
-- TOC entry 4915 (class 1259 OID 27230)
-- Name: idx_bookmarks_student; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_bookmarks_student ON public.notice_bookmarks USING btree (student_id);


--
-- TOC entry 4902 (class 1259 OID 27226)
-- Name: idx_notices_academic_level; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_notices_academic_level ON public.notices USING btree (academic_level);


--
-- TOC entry 4903 (class 1259 OID 27224)
-- Name: idx_notices_category; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_notices_category ON public.notices USING btree (category_id);


--
-- TOC entry 4904 (class 1259 OID 27227)
-- Name: idx_notices_date_published; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_notices_date_published ON public.notices USING btree (date_published);


--
-- TOC entry 4905 (class 1259 OID 27223)
-- Name: idx_notices_status; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_notices_status ON public.notices USING btree (status);


--
-- TOC entry 4906 (class 1259 OID 27228)
-- Name: idx_notices_title; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_notices_title ON public.notices USING btree (title);


--
-- TOC entry 4907 (class 1259 OID 27225)
-- Name: idx_notices_urgency; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_notices_urgency ON public.notices USING btree (urgency_level);


--
-- TOC entry 4884 (class 1259 OID 27222)
-- Name: idx_students_academic_level; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_students_academic_level ON public.students USING btree (academic_level);


--
-- TOC entry 4885 (class 1259 OID 27221)
-- Name: idx_students_matric_number; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_students_matric_number ON public.students USING btree (matric_number);


--
-- TOC entry 4876 (class 1259 OID 27220)
-- Name: idx_users_email; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_users_email ON public.users USING btree (email);


--
-- TOC entry 4877 (class 1259 OID 27219)
-- Name: idx_users_username; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_users_username ON public.users USING btree (username);


--
-- TOC entry 4910 (class 1259 OID 27229)
-- Name: idx_views_student; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_views_student ON public.notice_views USING btree (student_id);


--
-- TOC entry 4925 (class 2606 OID 27101)
-- Name: administrators fk_administrators_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administrators
    ADD CONSTRAINT fk_administrators_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON DELETE CASCADE;


--
-- TOC entry 4932 (class 2606 OID 27214)
-- Name: audit_logs fk_audit_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.audit_logs
    ADD CONSTRAINT fk_audit_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON DELETE CASCADE;


--
-- TOC entry 4930 (class 2606 OID 27192)
-- Name: notice_bookmarks fk_bookmarks_notice; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notice_bookmarks
    ADD CONSTRAINT fk_bookmarks_notice FOREIGN KEY (notice_id) REFERENCES public.notices(notice_id) ON DELETE CASCADE;


--
-- TOC entry 4931 (class 2606 OID 27197)
-- Name: notice_bookmarks fk_bookmarks_student; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notice_bookmarks
    ADD CONSTRAINT fk_bookmarks_student FOREIGN KEY (student_id) REFERENCES public.students(student_id) ON DELETE CASCADE;


--
-- TOC entry 4926 (class 2606 OID 27144)
-- Name: notices fk_notices_category; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notices
    ADD CONSTRAINT fk_notices_category FOREIGN KEY (category_id) REFERENCES public.notice_categories(category_id);


--
-- TOC entry 4927 (class 2606 OID 27149)
-- Name: notices fk_notices_creator; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notices
    ADD CONSTRAINT fk_notices_creator FOREIGN KEY (created_by) REFERENCES public.users(user_id);


--
-- TOC entry 4924 (class 2606 OID 27082)
-- Name: students fk_students_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT fk_students_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON DELETE CASCADE;


--
-- TOC entry 4923 (class 2606 OID 27062)
-- Name: users fk_users_role; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES public.roles(role_id);


--
-- TOC entry 4928 (class 2606 OID 27168)
-- Name: notice_views fk_views_notice; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notice_views
    ADD CONSTRAINT fk_views_notice FOREIGN KEY (notice_id) REFERENCES public.notices(notice_id) ON DELETE CASCADE;


--
-- TOC entry 4929 (class 2606 OID 27173)
-- Name: notice_views fk_views_student; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notice_views
    ADD CONSTRAINT fk_views_student FOREIGN KEY (student_id) REFERENCES public.students(student_id) ON DELETE CASCADE;


-- Completed on 2026-07-21 15:00:31

--
-- PostgreSQL database dump complete
--

\unrestrict bfDUnLgQx64j5EwmJ2YT9pAdQhrgjbGZZ5x4kEd171PXM8PPhjVG6FkNkaRumpJ

