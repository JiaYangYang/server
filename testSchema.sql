INSERT INTO public.user (username, password) VALUES ('test','411ae2cd40a66da03795383eca7ce797');
INSERT INTO public.user_role (user_id, role_type) VALUES (1,1);

INSERT INTO public.user (username, password) VALUES ('guest','e22ea570f314612eb0e6241386c49fed');
INSERT INTO public.user_role (user_id, role_type) VALUES (2,3);

INSERT INTO public.event_type (name, created) VALUES ('Camping',now());
INSERT INTO public.event_type (name, created) VALUES ('Walking',now());