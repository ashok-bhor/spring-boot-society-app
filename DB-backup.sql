PGDMP  $                    |            societybld8    14.12    16.3 s    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16395    societybld8    DATABASE     ~   CREATE DATABASE societybld8 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_India.1252';
    DROP DATABASE societybld8;
                admin    false                        2615    2200    public    SCHEMA     2   -- *not* creating schema, since initdb creates it
 2   -- *not* dropping schema, since initdb creates it
                postgres    false            �           0    0    SCHEMA public    ACL     Q   REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   postgres    false    4                        2615    16611    sales    SCHEMA        CREATE SCHEMA sales;
    DROP SCHEMA sales;
                admin    false                        2615    16438    society    SCHEMA        CREATE SCHEMA society;
    DROP SCHEMA society;
                admin    false            �            1259    16397    extrachargesentry    TABLE     �   CREATE TABLE public.extrachargesentry (
    id bigint NOT NULL,
    chargedamount double precision NOT NULL,
    comments character varying(255),
    flatnumber integer NOT NULL,
    reason character varying(255)
);
 %   DROP TABLE public.extrachargesentry;
       public         heap    admin    false    4            �            1259    16396    extrachargesentry_id_seq    SEQUENCE     �   CREATE SEQUENCE public.extrachargesentry_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.extrachargesentry_id_seq;
       public          admin    false    212    4            �           0    0    extrachargesentry_id_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.extrachargesentry_id_seq OWNED BY public.extrachargesentry.id;
          public          admin    false    211            �            1259    16405    flat_type_flat_number_map    TABLE     �   CREATE TABLE public.flat_type_flat_number_map (
    flatnumber integer NOT NULL,
    annualmaintenance double precision NOT NULL,
    flattype character varying(255)
);
 -   DROP TABLE public.flat_type_flat_number_map;
       public         heap    admin    false    4            �            1259    16410    maintenancemasterentry    TABLE       CREATE TABLE public.maintenancemasterentry (
    flatnumber integer NOT NULL,
    chargedamount double precision NOT NULL,
    currentyear character varying(255),
    lastrecieveddate date,
    receivedtillnow double precision NOT NULL,
    totaloutstanding double precision NOT NULL
);
 *   DROP TABLE public.maintenancemasterentry;
       public         heap    admin    false    4            �            1259    16415 $   society_maintenance_values_year_wise    TABLE     �   CREATE TABLE public.society_maintenance_values_year_wise (
    recordnum integer NOT NULL,
    financialyear character varying(255),
    flattype character varying(255),
    maintenancevalue double precision NOT NULL
);
 8   DROP TABLE public.society_maintenance_values_year_wise;
       public         heap    admin    false    4            �            1259    16422    societymaintenanceentry    TABLE     %  CREATE TABLE public.societymaintenanceentry (
    flatnumber integer NOT NULL,
    amount double precision NOT NULL,
    annualmaintenance double precision NOT NULL,
    date date,
    flattype character varying(255),
    paidinyear double precision NOT NULL,
    paidtillyear character varying(255),
    paymentmethod character varying(255),
    previousoutstanding double precision NOT NULL,
    receivedtillnow double precision NOT NULL,
    transactionid character varying(255),
    verified boolean NOT NULL,
    year character varying(255)
);
 +   DROP TABLE public.societymaintenanceentry;
       public         heap    admin    false    4            �            1259    16430    societymaintenancepaidhistory    TABLE     �  CREATE TABLE public.societymaintenancepaidhistory (
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
    verified boolean NOT NULL,
    year character varying(255)
);
 1   DROP TABLE public.societymaintenancepaidhistory;
       public         heap    admin    false    4            �            1259    16429 $   societymaintenancepaidhistory_id_seq    SEQUENCE     �   CREATE SEQUENCE public.societymaintenancepaidhistory_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ;   DROP SEQUENCE public.societymaintenancepaidhistory_id_seq;
       public          admin    false    4    218            �           0    0 $   societymaintenancepaidhistory_id_seq    SEQUENCE OWNED BY     m   ALTER SEQUENCE public.societymaintenancepaidhistory_id_seq OWNED BY public.societymaintenancepaidhistory.id;
          public          admin    false    217            �            1259    16536    users    TABLE     �   CREATE TABLE public.users (
    user_id integer NOT NULL,
    email character varying(45) NOT NULL,
    full_name character varying(45) NOT NULL,
    password character varying(64) NOT NULL,
    enabled smallint
);
    DROP TABLE public.users;
       public         heap    postgres    false    4            �            1259    16535    users_user_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.users_user_id_seq;
       public          postgres    false    4    228            �           0    0    users_user_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;
          public          postgres    false    227            �            1259    16644    product    TABLE     �   CREATE TABLE sales.product (
    id bigint NOT NULL,
    brand character varying(255),
    madein character varying(255),
    name character varying(255),
    price real NOT NULL
);
    DROP TABLE sales.product;
       sales         heap    admin    false    7            �            1259    16643    product_id_seq    SEQUENCE     v   CREATE SEQUENCE sales.product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE sales.product_id_seq;
       sales          admin    false    7    235            �           0    0    product_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE sales.product_id_seq OWNED BY sales.product.id;
          sales          admin    false    234            �            1259    16653    roles    TABLE     \   CREATE TABLE sales.roles (
    role_id integer NOT NULL,
    name character varying(255)
);
    DROP TABLE sales.roles;
       sales         heap    admin    false    7            �            1259    16652    roles_role_id_seq    SEQUENCE     �   CREATE SEQUENCE sales.roles_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE sales.roles_role_id_seq;
       sales          admin    false    7    237            �           0    0    roles_role_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE sales.roles_role_id_seq OWNED BY sales.roles.role_id;
          sales          admin    false    236            �            1259    16660    users    TABLE     �   CREATE TABLE sales.users (
    user_id bigint NOT NULL,
    enabled boolean NOT NULL,
    password character varying(255),
    username character varying(255)
);
    DROP TABLE sales.users;
       sales         heap    admin    false    7            �            1259    16668    users_roles    TABLE     ^   CREATE TABLE sales.users_roles (
    user_id bigint NOT NULL,
    role_id integer NOT NULL
);
    DROP TABLE sales.users_roles;
       sales         heap    admin    false    7            �            1259    16659    users_user_id_seq    SEQUENCE     y   CREATE SEQUENCE sales.users_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE sales.users_user_id_seq;
       sales          admin    false    239    7            �           0    0    users_user_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE sales.users_user_id_seq OWNED BY sales.users.user_id;
          sales          admin    false    238            �            1259    16440    extrachargesentry    TABLE     �   CREATE TABLE society.extrachargesentry (
    id bigint NOT NULL,
    chargedamount double precision NOT NULL,
    comments character varying(255),
    flatnumber integer NOT NULL,
    reason character varying(255)
);
 &   DROP TABLE society.extrachargesentry;
       society         heap    admin    false    6            �            1259    16439    extrachargesentry_id_seq    SEQUENCE     �   CREATE SEQUENCE society.extrachargesentry_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE society.extrachargesentry_id_seq;
       society          admin    false    6    220            �           0    0    extrachargesentry_id_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE society.extrachargesentry_id_seq OWNED BY society.extrachargesentry.id;
          society          admin    false    219            �            1259    16448    flat_type_flat_number_map    TABLE     �   CREATE TABLE society.flat_type_flat_number_map (
    flatnumber integer NOT NULL,
    annualmaintenance double precision NOT NULL,
    flattype character varying(255)
);
 .   DROP TABLE society.flat_type_flat_number_map;
       society         heap    admin    false    6            �            1259    16453    maintenancemasterentry    TABLE        CREATE TABLE society.maintenancemasterentry (
    flatnumber integer NOT NULL,
    chargedamount double precision NOT NULL,
    currentyear character varying(255),
    lastrecieveddate date,
    receivedtillnow double precision NOT NULL,
    totaloutstanding double precision NOT NULL
);
 +   DROP TABLE society.maintenancemasterentry;
       society         heap    admin    false    6            �            1259    16588    roles    TABLE     f   CREATE TABLE society.roles (
    role_id integer NOT NULL,
    name character varying(50) NOT NULL
);
    DROP TABLE society.roles;
       society         heap    postgres    false    6            �            1259    16587    roles_role_id_seq    SEQUENCE     �   CREATE SEQUENCE society.roles_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE society.roles_role_id_seq;
       society          postgres    false    6    232            �           0    0    roles_role_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE society.roles_role_id_seq OWNED BY society.roles.role_id;
          society          postgres    false    231            �            1259    16458 $   society_maintenance_values_year_wise    TABLE     �   CREATE TABLE society.society_maintenance_values_year_wise (
    recordnum integer NOT NULL,
    financialyear character varying(255),
    flattype character varying(255),
    maintenancevalue double precision NOT NULL
);
 9   DROP TABLE society.society_maintenance_values_year_wise;
       society         heap    admin    false    6            �            1259    16465    societymaintenanceentry    TABLE     &  CREATE TABLE society.societymaintenanceentry (
    flatnumber integer NOT NULL,
    amount double precision NOT NULL,
    annualmaintenance double precision NOT NULL,
    date date,
    flattype character varying(255),
    paidinyear double precision NOT NULL,
    paidtillyear character varying(255),
    paymentmethod character varying(255),
    previousoutstanding double precision NOT NULL,
    receivedtillnow double precision NOT NULL,
    transactionid character varying(255),
    verified boolean NOT NULL,
    year character varying(255)
);
 ,   DROP TABLE society.societymaintenanceentry;
       society         heap    admin    false    6            �            1259    16473    societymaintenancepaidhistory    TABLE     �  CREATE TABLE society.societymaintenancepaidhistory (
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
    verified boolean NOT NULL,
    year character varying(255)
);
 2   DROP TABLE society.societymaintenancepaidhistory;
       society         heap    admin    false    6            �            1259    16472 $   societymaintenancepaidhistory_id_seq    SEQUENCE     �   CREATE SEQUENCE society.societymaintenancepaidhistory_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 <   DROP SEQUENCE society.societymaintenancepaidhistory_id_seq;
       society          admin    false    6    226            �           0    0 $   societymaintenancepaidhistory_id_seq    SEQUENCE OWNED BY     o   ALTER SEQUENCE society.societymaintenancepaidhistory_id_seq OWNED BY society.societymaintenancepaidhistory.id;
          society          admin    false    225            �            1259    16579    users    TABLE     �   CREATE TABLE society.users (
    user_id integer NOT NULL,
    username character varying(45) NOT NULL,
    password character varying(64) NOT NULL,
    enabled smallint
);
    DROP TABLE society.users;
       society         heap    postgres    false    6            �            1259    16596    users_roles    TABLE     a   CREATE TABLE society.users_roles (
    user_id integer NOT NULL,
    role_id integer NOT NULL
);
     DROP TABLE society.users_roles;
       society         heap    postgres    false    6            �            1259    16578    users_user_id_seq    SEQUENCE     �   CREATE SEQUENCE society.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE society.users_user_id_seq;
       society          postgres    false    230    6            �           0    0    users_user_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE society.users_user_id_seq OWNED BY society.users.user_id;
          society          postgres    false    229            �           2604    16400    extrachargesentry id    DEFAULT     |   ALTER TABLE ONLY public.extrachargesentry ALTER COLUMN id SET DEFAULT nextval('public.extrachargesentry_id_seq'::regclass);
 C   ALTER TABLE public.extrachargesentry ALTER COLUMN id DROP DEFAULT;
       public          admin    false    212    211    212            �           2604    16433     societymaintenancepaidhistory id    DEFAULT     �   ALTER TABLE ONLY public.societymaintenancepaidhistory ALTER COLUMN id SET DEFAULT nextval('public.societymaintenancepaidhistory_id_seq'::regclass);
 O   ALTER TABLE public.societymaintenancepaidhistory ALTER COLUMN id DROP DEFAULT;
       public          admin    false    218    217    218            �           2604    16539    users user_id    DEFAULT     n   ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);
 <   ALTER TABLE public.users ALTER COLUMN user_id DROP DEFAULT;
       public          postgres    false    227    228    228            �           2604    16647 
   product id    DEFAULT     f   ALTER TABLE ONLY sales.product ALTER COLUMN id SET DEFAULT nextval('sales.product_id_seq'::regclass);
 8   ALTER TABLE sales.product ALTER COLUMN id DROP DEFAULT;
       sales          admin    false    235    234    235            �           2604    16656    roles role_id    DEFAULT     l   ALTER TABLE ONLY sales.roles ALTER COLUMN role_id SET DEFAULT nextval('sales.roles_role_id_seq'::regclass);
 ;   ALTER TABLE sales.roles ALTER COLUMN role_id DROP DEFAULT;
       sales          admin    false    236    237    237            �           2604    16663    users user_id    DEFAULT     l   ALTER TABLE ONLY sales.users ALTER COLUMN user_id SET DEFAULT nextval('sales.users_user_id_seq'::regclass);
 ;   ALTER TABLE sales.users ALTER COLUMN user_id DROP DEFAULT;
       sales          admin    false    238    239    239            �           2604    16443    extrachargesentry id    DEFAULT     ~   ALTER TABLE ONLY society.extrachargesentry ALTER COLUMN id SET DEFAULT nextval('society.extrachargesentry_id_seq'::regclass);
 D   ALTER TABLE society.extrachargesentry ALTER COLUMN id DROP DEFAULT;
       society          admin    false    220    219    220            �           2604    16591    roles role_id    DEFAULT     p   ALTER TABLE ONLY society.roles ALTER COLUMN role_id SET DEFAULT nextval('society.roles_role_id_seq'::regclass);
 =   ALTER TABLE society.roles ALTER COLUMN role_id DROP DEFAULT;
       society          postgres    false    232    231    232            �           2604    16476     societymaintenancepaidhistory id    DEFAULT     �   ALTER TABLE ONLY society.societymaintenancepaidhistory ALTER COLUMN id SET DEFAULT nextval('society.societymaintenancepaidhistory_id_seq'::regclass);
 P   ALTER TABLE society.societymaintenancepaidhistory ALTER COLUMN id DROP DEFAULT;
       society          admin    false    226    225    226            �           2604    16582    users user_id    DEFAULT     p   ALTER TABLE ONLY society.users ALTER COLUMN user_id SET DEFAULT nextval('society.users_user_id_seq'::regclass);
 =   ALTER TABLE society.users ALTER COLUMN user_id DROP DEFAULT;
       society          postgres    false    229    230    230            {          0    16397    extrachargesentry 
   TABLE DATA           \   COPY public.extrachargesentry (id, chargedamount, comments, flatnumber, reason) FROM stdin;
    public          admin    false    212   P�       |          0    16405    flat_type_flat_number_map 
   TABLE DATA           \   COPY public.flat_type_flat_number_map (flatnumber, annualmaintenance, flattype) FROM stdin;
    public          admin    false    213   m�       }          0    16410    maintenancemasterentry 
   TABLE DATA           �   COPY public.maintenancemasterentry (flatnumber, chargedamount, currentyear, lastrecieveddate, receivedtillnow, totaloutstanding) FROM stdin;
    public          admin    false    214   �       ~          0    16415 $   society_maintenance_values_year_wise 
   TABLE DATA           t   COPY public.society_maintenance_values_year_wise (recordnum, financialyear, flattype, maintenancevalue) FROM stdin;
    public          admin    false    215   �                 0    16422    societymaintenanceentry 
   TABLE DATA           �   COPY public.societymaintenanceentry (flatnumber, amount, annualmaintenance, date, flattype, paidinyear, paidtillyear, paymentmethod, previousoutstanding, receivedtillnow, transactionid, verified, year) FROM stdin;
    public          admin    false    216   ��       �          0    16430    societymaintenancepaidhistory 
   TABLE DATA           �   COPY public.societymaintenancepaidhistory (id, amount, annualmaintenance, date, deleted, deletedreason, flatnumber, flattype, paymentmethod, transactionid, verified, year) FROM stdin;
    public          admin    false    218   ʐ       �          0    16536    users 
   TABLE DATA           M   COPY public.users (user_id, email, full_name, password, enabled) FROM stdin;
    public          postgres    false    228   �       �          0    16644    product 
   TABLE DATA           @   COPY sales.product (id, brand, madein, name, price) FROM stdin;
    sales          admin    false    235   �       �          0    16653    roles 
   TABLE DATA           -   COPY sales.roles (role_id, name) FROM stdin;
    sales          admin    false    237   4�       �          0    16660    users 
   TABLE DATA           D   COPY sales.users (user_id, enabled, password, username) FROM stdin;
    sales          admin    false    239   p�       �          0    16668    users_roles 
   TABLE DATA           6   COPY sales.users_roles (user_id, role_id) FROM stdin;
    sales          admin    false    240   ��       �          0    16440    extrachargesentry 
   TABLE DATA           ]   COPY society.extrachargesentry (id, chargedamount, comments, flatnumber, reason) FROM stdin;
    society          admin    false    220   Ғ       �          0    16448    flat_type_flat_number_map 
   TABLE DATA           ]   COPY society.flat_type_flat_number_map (flatnumber, annualmaintenance, flattype) FROM stdin;
    society          admin    false    221   �       �          0    16453    maintenancemasterentry 
   TABLE DATA           �   COPY society.maintenancemasterentry (flatnumber, chargedamount, currentyear, lastrecieveddate, receivedtillnow, totaloutstanding) FROM stdin;
    society          admin    false    222   ��       �          0    16588    roles 
   TABLE DATA           /   COPY society.roles (role_id, name) FROM stdin;
    society          postgres    false    232   ӓ       �          0    16458 $   society_maintenance_values_year_wise 
   TABLE DATA           u   COPY society.society_maintenance_values_year_wise (recordnum, financialyear, flattype, maintenancevalue) FROM stdin;
    society          admin    false    223   ��       �          0    16465    societymaintenanceentry 
   TABLE DATA           �   COPY society.societymaintenanceentry (flatnumber, amount, annualmaintenance, date, flattype, paidinyear, paidtillyear, paymentmethod, previousoutstanding, receivedtillnow, transactionid, verified, year) FROM stdin;
    society          admin    false    224   ��       �          0    16473    societymaintenancepaidhistory 
   TABLE DATA           �   COPY society.societymaintenancepaidhistory (id, amount, annualmaintenance, date, deleted, deletedreason, flatnumber, flattype, paymentmethod, transactionid, verified, year) FROM stdin;
    society          admin    false    226   �       �          0    16579    users 
   TABLE DATA           F   COPY society.users (user_id, username, password, enabled) FROM stdin;
    society          postgres    false    230   ו       �          0    16596    users_roles 
   TABLE DATA           8   COPY society.users_roles (user_id, role_id) FROM stdin;
    society          postgres    false    233   z�       �           0    0    extrachargesentry_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.extrachargesentry_id_seq', 1, false);
          public          admin    false    211            �           0    0 $   societymaintenancepaidhistory_id_seq    SEQUENCE SET     S   SELECT pg_catalog.setval('public.societymaintenancepaidhistory_id_seq', 1, false);
          public          admin    false    217            �           0    0    users_user_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.users_user_id_seq', 1, false);
          public          postgres    false    227            �           0    0    product_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('sales.product_id_seq', 1, true);
          sales          admin    false    234            �           0    0    roles_role_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('sales.roles_role_id_seq', 4, true);
          sales          admin    false    236            �           0    0    users_user_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('sales.users_user_id_seq', 5, true);
          sales          admin    false    238            �           0    0    extrachargesentry_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('society.extrachargesentry_id_seq', 33, true);
          society          admin    false    219            �           0    0    roles_role_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('society.roles_role_id_seq', 1, false);
          society          postgres    false    231            �           0    0 $   societymaintenancepaidhistory_id_seq    SEQUENCE SET     S   SELECT pg_catalog.setval('society.societymaintenancepaidhistory_id_seq', 8, true);
          society          admin    false    225            �           0    0    users_user_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('society.users_user_id_seq', 2, true);
          society          postgres    false    229            �           2606    16404 (   extrachargesentry extrachargesentry_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.extrachargesentry
    ADD CONSTRAINT extrachargesentry_pkey PRIMARY KEY (id);
 R   ALTER TABLE ONLY public.extrachargesentry DROP CONSTRAINT extrachargesentry_pkey;
       public            admin    false    212            �           2606    16409 8   flat_type_flat_number_map flat_type_flat_number_map_pkey 
   CONSTRAINT     ~   ALTER TABLE ONLY public.flat_type_flat_number_map
    ADD CONSTRAINT flat_type_flat_number_map_pkey PRIMARY KEY (flatnumber);
 b   ALTER TABLE ONLY public.flat_type_flat_number_map DROP CONSTRAINT flat_type_flat_number_map_pkey;
       public            admin    false    213            �           2606    16414 2   maintenancemasterentry maintenancemasterentry_pkey 
   CONSTRAINT     x   ALTER TABLE ONLY public.maintenancemasterentry
    ADD CONSTRAINT maintenancemasterentry_pkey PRIMARY KEY (flatnumber);
 \   ALTER TABLE ONLY public.maintenancemasterentry DROP CONSTRAINT maintenancemasterentry_pkey;
       public            admin    false    214            �           2606    16421 N   society_maintenance_values_year_wise society_maintenance_values_year_wise_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.society_maintenance_values_year_wise
    ADD CONSTRAINT society_maintenance_values_year_wise_pkey PRIMARY KEY (recordnum);
 x   ALTER TABLE ONLY public.society_maintenance_values_year_wise DROP CONSTRAINT society_maintenance_values_year_wise_pkey;
       public            admin    false    215            �           2606    16428 4   societymaintenanceentry societymaintenanceentry_pkey 
   CONSTRAINT     z   ALTER TABLE ONLY public.societymaintenanceentry
    ADD CONSTRAINT societymaintenanceentry_pkey PRIMARY KEY (flatnumber);
 ^   ALTER TABLE ONLY public.societymaintenanceentry DROP CONSTRAINT societymaintenanceentry_pkey;
       public            admin    false    216            �           2606    16437 @   societymaintenancepaidhistory societymaintenancepaidhistory_pkey 
   CONSTRAINT     ~   ALTER TABLE ONLY public.societymaintenancepaidhistory
    ADD CONSTRAINT societymaintenancepaidhistory_pkey PRIMARY KEY (id);
 j   ALTER TABLE ONLY public.societymaintenancepaidhistory DROP CONSTRAINT societymaintenancepaidhistory_pkey;
       public            admin    false    218            �           2606    16543    users users_email_key 
   CONSTRAINT     Q   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);
 ?   ALTER TABLE ONLY public.users DROP CONSTRAINT users_email_key;
       public            postgres    false    228            �           2606    16541    users users_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    228            �           2606    16651    product product_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY sales.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);
 =   ALTER TABLE ONLY sales.product DROP CONSTRAINT product_pkey;
       sales            admin    false    235            �           2606    16658    roles roles_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY sales.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (role_id);
 9   ALTER TABLE ONLY sales.roles DROP CONSTRAINT roles_pkey;
       sales            admin    false    237            �           2606    16667    users users_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY sales.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);
 9   ALTER TABLE ONLY sales.users DROP CONSTRAINT users_pkey;
       sales            admin    false    239            �           2606    16672    users_roles users_roles_pkey 
   CONSTRAINT     g   ALTER TABLE ONLY sales.users_roles
    ADD CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id);
 E   ALTER TABLE ONLY sales.users_roles DROP CONSTRAINT users_roles_pkey;
       sales            admin    false    240    240            �           2606    16447 (   extrachargesentry extrachargesentry_pkey 
   CONSTRAINT     g   ALTER TABLE ONLY society.extrachargesentry
    ADD CONSTRAINT extrachargesentry_pkey PRIMARY KEY (id);
 S   ALTER TABLE ONLY society.extrachargesentry DROP CONSTRAINT extrachargesentry_pkey;
       society            admin    false    220            �           2606    16452 8   flat_type_flat_number_map flat_type_flat_number_map_pkey 
   CONSTRAINT        ALTER TABLE ONLY society.flat_type_flat_number_map
    ADD CONSTRAINT flat_type_flat_number_map_pkey PRIMARY KEY (flatnumber);
 c   ALTER TABLE ONLY society.flat_type_flat_number_map DROP CONSTRAINT flat_type_flat_number_map_pkey;
       society            admin    false    221            �           2606    16457 2   maintenancemasterentry maintenancemasterentry_pkey 
   CONSTRAINT     y   ALTER TABLE ONLY society.maintenancemasterentry
    ADD CONSTRAINT maintenancemasterentry_pkey PRIMARY KEY (flatnumber);
 ]   ALTER TABLE ONLY society.maintenancemasterentry DROP CONSTRAINT maintenancemasterentry_pkey;
       society            admin    false    222            �           2606    16595    roles roles_name_key 
   CONSTRAINT     P   ALTER TABLE ONLY society.roles
    ADD CONSTRAINT roles_name_key UNIQUE (name);
 ?   ALTER TABLE ONLY society.roles DROP CONSTRAINT roles_name_key;
       society            postgres    false    232            �           2606    16593    roles roles_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY society.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (role_id);
 ;   ALTER TABLE ONLY society.roles DROP CONSTRAINT roles_pkey;
       society            postgres    false    232            �           2606    16464 N   society_maintenance_values_year_wise society_maintenance_values_year_wise_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY society.society_maintenance_values_year_wise
    ADD CONSTRAINT society_maintenance_values_year_wise_pkey PRIMARY KEY (recordnum);
 y   ALTER TABLE ONLY society.society_maintenance_values_year_wise DROP CONSTRAINT society_maintenance_values_year_wise_pkey;
       society            admin    false    223            �           2606    16471 4   societymaintenanceentry societymaintenanceentry_pkey 
   CONSTRAINT     {   ALTER TABLE ONLY society.societymaintenanceentry
    ADD CONSTRAINT societymaintenanceentry_pkey PRIMARY KEY (flatnumber);
 _   ALTER TABLE ONLY society.societymaintenanceentry DROP CONSTRAINT societymaintenanceentry_pkey;
       society            admin    false    224            �           2606    16480 @   societymaintenancepaidhistory societymaintenancepaidhistory_pkey 
   CONSTRAINT        ALTER TABLE ONLY society.societymaintenancepaidhistory
    ADD CONSTRAINT societymaintenancepaidhistory_pkey PRIMARY KEY (id);
 k   ALTER TABLE ONLY society.societymaintenancepaidhistory DROP CONSTRAINT societymaintenancepaidhistory_pkey;
       society            admin    false    226            �           2606    16584    users users_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY society.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);
 ;   ALTER TABLE ONLY society.users DROP CONSTRAINT users_pkey;
       society            postgres    false    230            �           2606    16600    users_roles users_roles_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY society.users_roles
    ADD CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id);
 G   ALTER TABLE ONLY society.users_roles DROP CONSTRAINT users_roles_pkey;
       society            postgres    false    233    233            �           2606    16586    users users_username_key 
   CONSTRAINT     X   ALTER TABLE ONLY society.users
    ADD CONSTRAINT users_username_key UNIQUE (username);
 C   ALTER TABLE ONLY society.users DROP CONSTRAINT users_username_key;
       society            postgres    false    230            �           2606    16678 '   users_roles fk2o0jvgh89lemvvo17cbqvdxaa    FK CONSTRAINT     �   ALTER TABLE ONLY sales.users_roles
    ADD CONSTRAINT fk2o0jvgh89lemvvo17cbqvdxaa FOREIGN KEY (user_id) REFERENCES sales.users(user_id);
 P   ALTER TABLE ONLY sales.users_roles DROP CONSTRAINT fk2o0jvgh89lemvvo17cbqvdxaa;
       sales          admin    false    240    239    3304            �           2606    16673 '   users_roles fkj6m8fwv7oqv74fcehir1a9ffy    FK CONSTRAINT     �   ALTER TABLE ONLY sales.users_roles
    ADD CONSTRAINT fkj6m8fwv7oqv74fcehir1a9ffy FOREIGN KEY (role_id) REFERENCES sales.roles(role_id);
 P   ALTER TABLE ONLY sales.users_roles DROP CONSTRAINT fkj6m8fwv7oqv74fcehir1a9ffy;
       sales          admin    false    237    3302    240            �           2606    16606 $   users_roles users_roles_role_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY society.users_roles
    ADD CONSTRAINT users_roles_role_id_fkey FOREIGN KEY (role_id) REFERENCES society.roles(role_id);
 O   ALTER TABLE ONLY society.users_roles DROP CONSTRAINT users_roles_role_id_fkey;
       society          postgres    false    3296    232    233            �           2606    16601 $   users_roles users_roles_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY society.users_roles
    ADD CONSTRAINT users_roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES society.users(user_id);
 O   ALTER TABLE ONLY society.users_roles DROP CONSTRAINT users_roles_user_id_fkey;
       society          postgres    false    230    233    3290            {      x������ � �      |   g   x�Uͱ�0D�:�gǡ�Bb��P�ߧ'[�bCjŶ���T��V4ӹ�?E+Zъ�i��i�6h�6h������jO.}�;tR'?'m��A;>�&��Z/E?L�      }      x������ � �      ~   �   x�Uϱ�0D��څy�#�M +d�9b	y���S�Ԇ����zo6 mG�~�<��G2wm#p����8�)`z
�f��0�s�_
���ҥq�s#��X�ti�tn�B@й�+7��ϩEE���K�ӹq/t�^n<J��H��go�}��`Z            x������ � �      �      x������ � �      �      x������ � �      �       x�3�t2���K�L�0�4400������ B��      �   ,   x�3�v�2�tru��2�tu�1L8]|=��b���� �j>      �      x�E�Kr�0  �59��|u)�,���L7�c��4��]��v�P6���M���5'Cq]rm`	���ެ�խ��^Fh)6�|�T��c��D���m���h�������OC"��<v	D���Ըw+<eE�C�ᨐg3}]��_>6y�X�j(��u��4�(����_=�JO���lц(���c]p���{x&a1=�|ys<y��C�q���m�z�hQ#U豖ǃ�	k0�&�S���!�Mh�S�Kf9���k�mxɭ�K%m�.�ݩ@|rnP!5�8�� ���o�      �   "   x�3�4�2�4�2�4�2�&@ڔӄ+F��� 4�}      �   9   x�36�42�4��/-).I�K��KW�OS0204�朦ƜE��E
HJ�b���� ���      �   g   x�Uͱ�0D�:�gǡ�Bb��P�ߧ'[�bCjŶ���T��V4ӹ�?E+Zъ�i��i�6h�6h������jO.}�;tR'?'m��A;>�&��Z/E?L�      �   1   x�350�42�4�4202�52����朆�����&&&\1z\\\ ���      �      x�3�tt����2�v����� +��      �   �   x�Uϱ�0D��څy�#�M +d�9b	y���S�Ԇ����zo6 mG�~�<��G2wm#p����8�)`z
�f��0�s�_
���ҥq�s#��X�ti�tn�B@й�+7��ϩEE���K�ӹq/t�^n<J��H��go�}��`Z      �   L   x�350�44722��FF����朆NޜfPQ##�����TNcCS�SKC ehi`�YS����� �Jz      �   �   x�u��n1D���ͬ��n�
	�/�C ("����t׸�7�yvU�^=�eB�r�KE~mwr��n'!�!�cN�tF+2,�3���s(Z��f�숴2���+? �o|X�=s�?���@S/�L�h������|�;�lTAKM�aB?����'p�����"GO6_wkƚ8:�:�i:lRJ�e�      �   �   x�5���   �3<��ZˎZ��Ϙa��:��pͧ�K�|����p�!�K��Kԓ�t��rP��,��'�K�j�y"}q4��gT����(�(��[o�2�#���qi��݄iue�μ.�8��p�nz�{�+�{,�      �      x�3�4�2�4����� ��     