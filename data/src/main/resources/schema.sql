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

CREATE TABLE public.event_type (
  id       SERIAL PRIMARY KEY,
  name     VARCHAR   NOT NULL UNIQUE,
  created  TIMESTAMP NOT NULL DEFAULT now(),
  user_id  INT       NOT NULL REFERENCES public.user (id),
  disabled TIMESTAMP
);
ALTER TABLE public.event_type OWNER TO web_user;

CREATE TABLE public.event_template (
  id            SERIAL PRIMARY KEY,
  name          VARCHAR NOT NULL,
  event_type_id INT     NOT NULL REFERENCES public.event_type (id),
  user_id       INT     NOT NULL REFERENCES public.user (id),
  disabled      TIMESTAMP,
  UNIQUE (name, event_type_id)
);
ALTER TABLE public.event_template OWNER TO web_user;

CREATE TABLE public.event_template_version (
  id                SERIAL PRIMARY KEY,
  event_template_id INT       NOT NULL REFERENCES public.event_template (id),
  created           TIMESTAMP NOT NULL DEFAULT now(),
  user_id           INT       NOT NULL REFERENCES public.user (id),
  disabled          TIMESTAMP
);
ALTER TABLE public.event_template_version OWNER TO web_user;

CREATE TABLE public.event_template_widget_type (
  id            SERIAL PRIMARY KEY,
  name          VARCHAR NOT NULL,
  label         VARCHAR NOT NULL UNIQUE,
  image_upload  BOOLEAN DEFAULT FALSE,
  image_show    BOOLEAN DEFAULT FALSE,
  file_upload   BOOLEAN DEFAULT FALSE,
  file_download BOOLEAN DEFAULT FALSE
);
ALTER TABLE public.event_template_widget_type OWNER TO web_user;

CREATE TABLE public.event_template_widget_type_attribute (
  id                            SERIAL PRIMARY KEY,
  event_template_widget_type_id INT     NOT NULL REFERENCES public.event_template_widget_type (id),
  name                          VARCHAR NOT NULL,
  label                         VARCHAR NOT NULL,
  pattern                       VARCHAR,
  widget_type_id                INT REFERENCES public.event_template_widget_type (id)
);
ALTER TABLE public.event_template_widget_type_attribute OWNER TO web_user;

CREATE TABLE public.event_template_version_widget (
  id                            SERIAL PRIMARY KEY,
  event_template_version_id     INT     NOT NULL REFERENCES public.event_template_version (id),
  priority                      INT,
  label                         VARCHAR NOT NULL,
  event_template_widget_type_id INT REFERENCES public.event_template_widget_type (id)
);
ALTER TABLE public.event_template_version_widget OWNER TO web_user;

CREATE TABLE public.event_template_version_widget_attribute (
  id                                      SERIAL PRIMARY KEY,
  event_template_version_widget_id        INT     NOT NULL REFERENCES public.event_template_version_widget (id),
  event_template_widget_type_attribute_id INT     NOT NULL REFERENCES public.event_template_widget_type_attribute (id),
  value                                   VARCHAR NOT NULL
);
ALTER TABLE public.event_template_version_widget_attribute OWNER TO web_user;

CREATE TABLE public.file_record_type (
  id        SERIAL PRIMARY KEY,
  name      VARCHAR NOT NULL UNIQUE,
  entity    VARCHAR NOT NULL UNIQUE,
  directory VARCHAR NOT NULL
);
ALTER TABLE public.file_record_type OWNER TO web_user;

CREATE TABLE public.file_record (
  id                  SERIAL PRIMARY KEY,
  file_record_type_id INT     NOT NULL REFERENCES public.file_record_type (id),
  entity_id           INT,
  filename            VARCHAR NOT NULL,
  mime_type           VARCHAR NOT NULL,
  sub_dir             VARCHAR NOT NULL
);
ALTER TABLE public.file_record OWNER TO web_user;

CREATE TABLE public.event_instance (
  id                        SERIAL PRIMARY KEY,
  name                      VARCHAR   NOT NULL,
  event_template_version_id INT       NOT NULL REFERENCES public.event_template_version (id),
  created                   TIMESTAMP NOT NULL DEFAULT now(),
  user_id                   INT       NOT NULL REFERENCES public.user (id),
  disabled                  TIMESTAMP
);
ALTER TABLE public.event_instance OWNER TO web_user;