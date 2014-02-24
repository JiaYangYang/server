for tbl in `psql -qAt -c "select tablename from pg_tables where schemaname = 'public';" web_app` ; do psql -c "alter table public.$tbl owner to web_user" web_app ; done;
for tbl in `psql -qAt -c "select sequence_name from information_schema.sequences where sequence_schema = 'public';" web_app` ; do  psql -c "alter table public.$tbl owner to web_user" web_app ; done;
