
INSERT INTO SERGIO11.COUNTRIES (ID, "NAME") 
	VALUES (1, 'España');
INSERT INTO SERGIO11.COUNTRIES (ID, "NAME") 
	VALUES (2, 'Portugal');

INSERT INTO SERGIO11.PROVINCES (ID, COUNTRY, "NAME") 
	VALUES (1, 1, 'Ávila');
INSERT INTO SERGIO11.PROVINCES (ID, COUNTRY, "NAME") 
	VALUES (2, 1, 'Salamanca');
INSERT INTO SERGIO11.PROVINCES (ID, COUNTRY, "NAME") 
	VALUES (3, 1, 'Valladolid');
INSERT INTO SERGIO11.PROVINCES (ID, COUNTRY, "NAME") 
	VALUES (4, 1, 'Madrid');
INSERT INTO SERGIO11.PROVINCES (ID, COUNTRY, "NAME") 
	VALUES (5, 2, 'Lisboa');
INSERT INTO SERGIO11.PROVINCES (ID, COUNTRY, "NAME") 
	VALUES (6, 2, 'Oporto');
INSERT INTO SERGIO11.PROVINCES (ID, COUNTRY, "NAME") 
	VALUES (7, 2, 'Coimbra');


INSERT INTO SERGIO11.GROUPS ("NAME", DESCRIPTION) 
	VALUES ('USER', 'Group for Users');
INSERT INTO SERGIO11.GROUPS ("NAME", DESCRIPTION) 
	VALUES ('ADMINISTRATOR', 'Group for Administrator');

	La contraseña => bisite00 (para ambos usuarios)
	Uno tiene ambos roles por lo que podrá acceder a todas las páginas
	Otro sólo tiene el rol usuario por lo que no podrá acceder a "temas" o "usuarios"

INSERT INTO SERGIO11.USERS (USER_NAME, BIRTHDAY, EMAIL, LASTNAME, MOBILE, "NAME", PASSWD, PROVINCE_ID) 
	VALUES ('sergio11', '2016-10-29', 'sss4esob@gmail.com', 'Sánchez', 657894575, 'Sergio', 'eaa66f1a644c8a09ca3c584f415c0c49', NULL);
INSERT INTO SERGIO11.USERS (USER_NAME, BIRTHDAY, EMAIL, LASTNAME, MOBILE, "NAME", PASSWD, PROVINCE_ID) 
	VALUES ('pepito74', '2015-12-07', 'pepito@gmail.com', 'Martín', 657894575, 'Pepito', 'eaa66f1a644c8a09ca3c584f415c0c49', 1);

INSERT INTO SERGIO11.USER_GROUPS (USER_NAME, GROUP_NAME) 
	VALUES ('pepito74', 'USER');
INSERT INTO SERGIO11.USER_GROUPS (USER_NAME, GROUP_NAME) 
	VALUES ('sergio11', 'ADMINISTRATOR');
INSERT INTO SERGIO11.USER_GROUPS (USER_NAME, GROUP_NAME) 
	VALUES ('sergio11', 'USER');
