-- CREATION OF ADMIN IF NOT EXISTS
insert into public.users(id, email,role, pwd,first_name, last_name) 
SELECT nextval('public.user_seq'), 'pf1.fhg@gmail.com','ROLE_ADMIN','1622427325','admin', 'admin'
WHERE NOT EXISTS (select 1 from public.users where email = 'pf1.fhg@gmail.com');
