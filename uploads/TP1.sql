--conn sys as sysdba
---part1
--1
----select name nom, description   from v$BGPROCESS;
--2
---select count(*) nbr, owner from dba_objects group by owner; 


----pour chaque type d''object 
----select object_type type,  count(*) nombre from dba_objects group by object_type;

--3
/*

CREATE OR REPLACE FUNCTION FN_USERS RETURN NUMBER 
IS
v_count NUMBER;
BEGIN
SELECT COUNT(*) INTO v_count FROM dba_users WHERE TRUNC(created) >= TRUNC(SYSDATE, 'MONTH');
RETURN v_count;
END;
/
*/
----extract(month from sysdate)= extract(month from created)
---set serveroutput on 
---appel
/*select fn_users from dual ;
DECLARE
user_count NUMBER;
BEGIN
user_count := FN_USERS;
DBMS_OUTPUT.PUT_LINE('Nombre d''utilisateurs de ce mois-ci : ' || user_count);
END;
/
*/


---4
/*
CREATE OR REPLACE PROCEDURE PROC_TABLES(p_username VARCHAR) 
IS
BEGIN
FOR i IN (SELECT table_name nom FROM all_tables WHERE owner = upper (p_username)) 
LOOP
DBMS_OUTPUT.PUT_LINE('Table: ' || i.nom);
END LOOP;
END;
/
*/  
---- execute PROC_TABLES ('hr'); 
---autre exemple:  
----les noms du tables sur serveur 
-- avec curseur implicite 
/*
CREATE OR REPLACE PROCEDURE 
PROC_TABLES (a out integer)
IS
BEGIN
a:=0;
for i in (select table_name A
from user_tables) 
LOOP
a:=a+1;
dbms_output.put_line(i.A);
END LOOP;
END;
/

--APPEL
declare
a integer;
Begin
proc_list_tab(a);
dbms_output.put_line(a);
end;
/
*/

--avec le curseur explicite
/*
CREATE OR REPLACE PROCEDURE 
proc_list_tab (a out integer)
IS
cursor c is select table_name A
from user_tables;
i c%rowtype;
BEGIN
Open c;
Loop
fetch c into i;
exit when c%notfound;
dbms_output.put_line(i.A);
end loop;
a:=c%rowcount;
close c;
END;
/
*/
---5
/*
create or replace function fn_connexion 
return integer 
is 
nb_user integer ;
begin 
select count (*) into nb_user from v$session 
where type = 'USER' ;
return nb_user ;
 end ;
 /
*/

---6
/*	 
CREATE OR REPLACE FUNCTION FN_TOTAL
 RETURN NUMBER 
IS
 v_size NUMBER;
BEGIN
SELECT SUM(value) INTO v_total_size FROM v$sga;
RETURN v_size;
END;
/
*/
---select FN_TOTAL from dual; 

---7
/*
create or replace function FN_SESSION ( p_user varchar2 ) return date 
is 
d date ;
begin
select timestamp into d 
from dba_audit_session
where username= upper (p_user) ;
return d; 
end ;
/ 
*/
----select FN_SESSION ( 'hr' ) from dual; 
---8
/*
CREATE OR REPLACE FUNCTION FN_EXPIRED 
RETURN NUMBER 
IS
v_users NUMBER;
BEGIN
 SELECT COUNT(*) INTO v_users FROM dba_users WHERE account_status = 'EXPIRED';
RETURN v_users;
END;
/
*/
---9
/*

CREATE OR REPLACE PROCEDURE PROC_MEMORY( P_OWNER  VARCHAR, P_TABLE  VARCHAR2) 
IS
 v_block NUMBER;
BEGIN
SELECT blocks INTO v_block FROM dba_tables WHERE owner = upper (P_OWNER)
    AND table_name = upper (P_TABLE);
DBMS_OUTPUT.PUT_LINE('Number of blocks for table  is' || P_OWNER || '.' || P_TABLE || ': ' || v_block);
END;
/

*/ 
---appel
/*
BEGIN
 PROC_MEMORY('HR', 'DEPARTMENTS');
END;
/
*/
---10
/*
CREATE OR REPLACE FUNCTION FN_EXP_DATE(P_USER VARCHAR) RETURN DATE 
IS
 v_date DATE;
BEGIN
 SELECT expiry_date INTO v_date FROM dba_users WHERE username = upper (P_USER);

   IF v_date IS NULL THEN
      RETURN NULL; -- User does not have an expiration date
   ELSE
      RETURN v_date;
   END IF;
END;
/
*/


--part2
-------débloquer le compte HR : ALTER USER hr identified by hr account unlock;
--11

----CONN AVEC SYS 
/*
select count(*) nombre 
from dba_tables
where owner ='HR';
--ou
select count(*)
from dba_objects
where object_type='TABLE' and owner ='HR';
*/
---CONN AVEC HR
---select count(*) nombre  from ALL_tables;


---12

/*
set serveroutput on 
BEGIN
   FOR i IN (SELECT DISTINCT object_type  type FROM dba_objects)
   LOOP
      DBMS_OUTPUT.PUT_LINE('Type d''objet : ' || i.type);
      
      FOR j IN (SELECT object_name nom , created creation , last_ddl_time dernier
                      FROM dba_objects
                      WHERE object_type = i.type)
      LOOP
         DBMS_OUTPUT.PUT_LINE('Objet : ' || j.nom||'Date de creation : ' || j.creation|| 'Date de dernière modification : ' || j.dernier);
      END LOOP;
   END LOOP;
END;
/
*/
---ou bien un curseur parametre 


--13
---conn sys
/*
SELECT constraint_name, constraint_type
FROM all_constraints
WHERE table_name = 'DEPARTMENTS'
AND owner = 'HR';
*/
---hr

/*
SELECT constraint_name, constraint_type
FROM user_constraints
WHERE table_name = 'DEPARTMENTS';
*/

---14
/*

CREATE OR REPLACE FUNCTION FN_LAST_OBJ RETURN VARCHAR
 IS
   v_last VARCHAR;
BEGIN
 SELECT object_name INTO v_last FROM user_objects ORDER BY created DESC FETCH FIRST 1 ROW ONLY;
   RETURN v_last;
END ;
/

*/

----ou 
---select object_name  from user_objects where  timestamp=( select max(timestamp)from user_objects);