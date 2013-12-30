CREATE TABLE public.user (
  id       SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL UNIQUE,
  password VARCHAR NOT NULL,
  bind_ip VARCHAR UNIQUE,
  disabled TIMESTAMP
);
alter table public.user owner to web_user;

CREATE TABLE public.user_role (
  id        SERIAL PRIMARY KEY,
  user_id   INT NOT NULL REFERENCES public.user (id),
  role_type INT NOT NULL,
  UNIQUE (user_id, role_type)
);
alter table public.user_role owner to web_user;


CREATE TABLE public.session (
  id           SERIAL PRIMARY KEY,
  user_id      INT       NOT NULL REFERENCES public.user (id),
  ip           VARCHAR   NOT NULL,
  session_id   VARCHAR   NOT NULL,
  start_time   TIMESTAMP NOT NULL,
  expire_time  TIMESTAMP NOT NULL,
  last_request TIMESTAMP NOT NULL
);
alter table public.session owner to web_user;


CREATE TABLE public.user_log (
  id          SERIAL PRIMARY KEY,
  user_id     INT       NOT NULL REFERENCES public.user (id),
  ip          VARCHAR   NOT NULL,
  user_agent  VARCHAR,
  login_time  TIMESTAMP NOT NULL,
  logout_time TIMESTAMP
);
alter table public.user_log owner to web_user;
