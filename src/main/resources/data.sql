-- CREATION OF ADMIN IF NOT EXISTS
insert into public.users(id, email,role, pwd,first_name, last_name) 
SELECT nextval('public.user_seq'), 'pf1.fhg@gmail.com','ROLE_ADMIN','w/CIDX25ZjOVGxrEIAOuBGrdc56kjXq04G3fQf+5NJ8=','admin', 'admin'
WHERE NOT EXISTS (select 1 from public.users where email = 'pf1.fhg@gmail.com');

-- CREATION OF TEST USERS
insert into public.users(id, email,role, pwd,first_name, last_name) 
SELECT nextval('public.user_seq'), 'frederic.pareja.pf1@gmail.com','ROLE_USER','j6fX4gemMtgKyGsovDr+3InbGlGd1G4yTKGCzQ8yHcg=','Frédérick', 'Pareja'
WHERE NOT EXISTS (select 1 from public.users where email = 'frederic.pareja.pf1@gmail.com');

insert into public.users(id, email,role, pwd,first_name, last_name) 
SELECT nextval('public.user_seq'), 'hugues.poumeyrol.pf1@gmail.com','ROLE_USER','awIF3lFI4/z7G0pbM6kIGtYS0nV4I1H91AlKkjizmwQ=','Hugues', 'Poumeyrol'
WHERE NOT EXISTS (select 1 from public.users where email = 'hugues.poumeyrol.pf1@gmail.com');

insert into public.users(id, email,role, pwd,first_name, last_name) 
SELECT nextval('public.user_seq'), 'gabriel.wisniewski@gmail.com','ROLE_USER','bVzo69KXz4PJwzcyR06W1tyOqJl4HFbiRQmy56neSII=','Gabriel', 'Wisniewski'
WHERE NOT EXISTS (select 1 from public.users where email = 'gabriel.wisniewski@gmail.com');
