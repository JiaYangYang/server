CREATE TABLE public.user (
  id       SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL UNIQUE,
  password VARCHAR NOT NULL,
  bind_ip  VARCHAR UNIQUE,
  disabled TIMESTAMP
);
ALTER TABLE public.user OWNER TO web_user;

CREATE TABLE public.user_role (
  id        SERIAL PRIMARY KEY,
  user_id   INT NOT NULL REFERENCES public.user (id),
  role_type INT NOT NULL,
  UNIQUE (user_id, role_type)
);
ALTER TABLE public.user_role OWNER TO web_user;

CREATE TABLE public.session (
  id           SERIAL PRIMARY KEY,
  user_id      INT       NOT NULL REFERENCES public.user (id),
  ip           VARCHAR   NOT NULL,
  session_id   VARCHAR   NOT NULL,
  start_time   TIMESTAMP NOT NULL,
  expire_time  TIMESTAMP NOT NULL,
  last_request TIMESTAMP NOT NULL
);
ALTER TABLE public.session OWNER TO web_user;

CREATE TABLE public.user_log (
  id          SERIAL PRIMARY KEY,
  user_id     INT       NOT NULL REFERENCES public.user (id),
  ip          VARCHAR   NOT NULL,
  user_agent  VARCHAR,
  login_time  TIMESTAMP NOT NULL,
  logout_time TIMESTAMP
);
ALTER TABLE public.user_log OWNER TO web_user;

CREATE TABLE public.event_instance (
  id       SERIAL PRIMARY KEY,
  serializable     VARCHAR   NOT NULL,
  user_id  INT       NOT NULL REFERENCES public.user (id),
  created TIMESTAMP NOT NULL default now(),
  verified BOOLEAN NOT NULL DEFAULT FALSE,
  disabled TIMESTAMP
);
ALTER TABLE public.event_instance OWNER TO web_user;