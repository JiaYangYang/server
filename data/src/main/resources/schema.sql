CREATE TABLE public.user (
  id       SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL UNIQUE,
  password VARCHAR NOT NULL,
  bind_ip  VARCHAR UNIQUE,
  disabled TIMESTAMP
);

CREATE TABLE public.identification_type (
  id   SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE public.user_detail (
  id                        SERIAL PRIMARY KEY,
  user_id                   INT NOT NULL REFERENCES public.user (id) ON DELETE CASCADE ON UPDATE CASCADE,
  identification_number     VARCHAR,
  identification_type_id    INT REFERENCES public.identification_type (id) ON DELETE CASCADE ON UPDATE CASCADE,
  identification_attachment BYTEA,
  first_name                VARCHAR,
  last_name                 VARCHAR,
  nick_name                 VARCHAR,
  birthday                  DATE,
  home_ph                   VARCHAR,
  work_ph                   VARCHAR,
  mobile_ph                 VARCHAR,
  post_code                 VARCHAR,
  security_question1        VARCHAR,
  security_answer1          VARCHAR,
  country                   VARCHAR,
  state                     VARCHAR,
  city                      VARCHAR,
  verified                  TIMESTAMP,
  verified_by               INT REFERENCES public.user (id) ON DELETE SET NULL ON UPDATE SET NULL
);

CREATE TABLE public.user_role (
  id        SERIAL PRIMARY KEY,
  user_id   INT NOT NULL REFERENCES public.user (id) ON DELETE CASCADE ON UPDATE CASCADE,
  role_type INT NOT NULL,
  UNIQUE (user_id, role_type)
);

CREATE TABLE public.session (
  id           SERIAL PRIMARY KEY,
  user_id      INT       NOT NULL REFERENCES public.user (id) ON DELETE CASCADE ON UPDATE CASCADE,
  ip           VARCHAR   NOT NULL,
  session_id   VARCHAR   NOT NULL,
  start_time   TIMESTAMP NOT NULL,
  expire_time  TIMESTAMP NOT NULL,
  last_request TIMESTAMP NOT NULL
);

CREATE TABLE public.user_log (
  id          SERIAL PRIMARY KEY,
  user_id     INT       NOT NULL REFERENCES public.user (id) ON DELETE CASCADE ON UPDATE CASCADE,
  ip          VARCHAR   NOT NULL,
  user_agent  VARCHAR,
  login_time  TIMESTAMP NOT NULL,
  logout_time TIMESTAMP
);

CREATE TABLE public.event_type (
  id       SERIAL PRIMARY KEY,
  name     VARCHAR   NOT NULL UNIQUE,
  created  TIMESTAMP NOT NULL DEFAULT now(),
  disabled TIMESTAMP
);

CREATE TABLE public.event (
  id              SERIAL PRIMARY KEY,
  event_type_id   INT       NOT NULL REFERENCES public.event_type (id) ON DELETE CASCADE ON UPDATE CASCADE,
  created         TIMESTAMP NOT NULL,
  created_by      INT       NOT NULL REFERENCES public.user (id) ON DELETE CASCADE ON UPDATE CASCADE,
  disabled        TIMESTAMP,
  disabled_by     INT REFERENCES public.user (id) ON DELETE SET NULL ON UPDATE SET NULL,
  disabled_reason VARCHAR,
  expiry          TIMESTAMP,
  verified        TIMESTAMP,
  verified_by     INT REFERENCES public.user (id) ON DELETE SET NULL ON UPDATE SET NULL,
  verified_reason VARCHAR
);

CREATE TABLE public.event_version (
  id          SERIAL PRIMARY KEY,
  event_id    INT       NOT NULL REFERENCES public.event (id) ON DELETE CASCADE ON UPDATE CASCADE,
  event_name  VARCHAR   NOT NULL,
  person_name VARCHAR   NOT NULL,
  gender      VARCHAR   NOT NULL,
  mobile_ph   VARCHAR   NOT NULL,
  note        VARCHAR,
  start_time  TIMESTAMP NOT NULL,
  end_time    TIMESTAMP,
  updated     TIMESTAMP NOT NULL
);

CREATE TABLE public.event_location (
  id               SERIAL PRIMARY KEY,
  event_version_id INT     NOT NULL REFERENCES public.event_version (id) ON DELETE CASCADE ON UPDATE CASCADE,
  location         VARCHAR NOT NULL,
  priority         INT
);

CREATE TABLE public.fee_type (
  id         SERIAL PRIMARY KEY,
  name       VARCHAR NOT NULL UNIQUE,
  compulsory BOOLEAN NOT NULL,
  priority   INT
);

CREATE TABLE public.fee_instance (
  id               SERIAL PRIMARY KEY,
  fee_type_id      INT     NOT NULL REFERENCES public.fee_type (id) ON DELETE CASCADE ON UPDATE CASCADE,
  event_version_id INT     NOT NULL REFERENCES public.event_version (id) ON DELETE CASCADE ON UPDATE CASCADE,
  compulsory       BOOLEAN NOT NULL,
  pre_paid         BOOLEAN,
  amount           DECIMAL,
  note             VARCHAR
);

CREATE TABLE public.participant_field_requirement (
  id               SERIAL PRIMARY KEY,
  event_version_id INT     NOT NULL REFERENCES public.event_version (id) ON DELETE CASCADE ON UPDATE CASCADE,
  participant_name BOOLEAN NOT NULL DEFAULT TRUE,
  email            BOOLEAN NOT NULL DEFAULT FALSE,
  contact_phone    BOOLEAN NOT NULL DEFAULT TRUE,
  age              BOOLEAN NOT NULL DEFAULT FALSE
);