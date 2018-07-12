-- CREATION OF ADMIN IF NOT EXISTS
insert into public.users(id, email,role, pwd,first_name, last_name) 
SELECT nextval('public.user_seq'), 'pf1.fhg@gmail.com','ROLE_ADMIN','1622427325','admin', 'admin'
WHERE NOT EXISTS (select 1 from public.users where email = 'pf1.fhg@gmail.com');

-- CREATION OF TEST USERS
insert into public.users(id, email,role, pwd,first_name, last_name) 
SELECT nextval('public.user_seq'), 'frederic.pareja.pf1@gmail.com','ROLE_USER','123','Frédérick', 'Pareja'
WHERE NOT EXISTS (select 1 from public.users where email = 'frederic.pareja.pf1@gmail.com');

insert into public.users(id, email,role, pwd,first_name, last_name) 
SELECT nextval('public.user_seq'), 'hugues.poumeyrol.pf1@gmail.com','ROLE_USER','123','Hugues', 'Poumeyrol'
WHERE NOT EXISTS (select 1 from public.users where email = 'hugues.poumeyrol.pf1@gmail.com');

insert into public.users(id, email,role, pwd,first_name, last_name) 
SELECT nextval('public.user_seq'), 'gabriel.wisniewski@gmail.com','ROLE_USER','123','Gabriel', 'Wisniewski'
WHERE NOT EXISTS (select 1 from public.users where email = 'gabriel.wisniewski@gmail.com');
