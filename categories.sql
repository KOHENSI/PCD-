insert into categories (pid , name) values (0,"Ordinateur");
	insert into categories (pid , name) select id , "PC portable" from categories where name = "Ordinateur";
	insert into categories (pid , name) select id , "PC de bureau" from categories where name = "Ordinateur";
	insert into categories (pid , name) select id , "ALL IN ONE" from categories where name = "Ordinateur";
	insert into categories (pid , name) select id , "Serveur" from categories where name = "Ordinateur";

insert into categories (pid , name) values (0,"Tablette");

insert into categories (pid , name) values (0,"Ecran");

insert into categories (pid , name) values (0,"Image et Son");
	insert into categories (pid , name) select id , "Appareil Photo" from categories where name = "Image et Son";
	insert into categories (pid , name) select id , "Téléviseur" from categories where name = "Image et Son";
	insert into categories (pid , name) select id , "Videoprojecteur" from categories where name = "Image et Son";

insert into categories (pid , name) values (0,"Téléphone");
	insert into categories (pid , name) select id , "Téléphone portable" from categories where name = "Téléphone";
	insert into categories (pid , name) select id , "Smartphone" from categories where name = "Téléphone";
	insert into categories (pid , name) select id , "iPhone" from categories where name = "Téléphone";

insert into categories (pid , name) values (0,"Impression");
	insert into categories (pid , name) select id , "Imprimante Laser" from categories where name = "Impression";
		insert into categories (pid , name) select id , "Imprimante jet d'encre" from categories where name = "Impression";
	insert into categories (pid , name) select id , "Photocopieur" from categories where name = "Impression";
	insert into categories (pid , name) select id , "FAX" from categories where name = "Impression";
	insert into categories (pid , name) select id , "Consommables" from categories where name = "Impression";

insert into categories (pid , name) values (0,"Accessoires");
	insert into categories (pid , name) select id , "Clavier" from categories where name = "Accessoires";
	insert into categories (pid , name) select id , "Souris" from categories where name = "Accessoires";
	insert into categories (pid , name) select id , "Manette" from categories where name = "Accessoires";
	insert into categories (pid , name) select id , "Haut Parleur" from categories where name = "Accessoires";
	insert into categories (pid , name) select id , "Flash disque" from categories where name = "Accessoires";
	insert into categories (pid , name) select id , "Micro casque" from categories where name = "Accessoires";
	insert into categories (pid , name) select id , "Disque dur externe" from categories where name = "Accessoires";
	insert into categories (pid , name) select id , "Disque dur interne" from categories where name = "Accessoires";
	insert into categories (pid , name) select id , "Sac" from categories where name = "Accessoires";
	insert into categories (pid , name) select id , "autre" from categories where name = "Accessoires";

insert into categories (pid , name) values (0,"Logiciel");

insert into categories (pid , name) values (0,"NonInformatique");

create table products ( id int primary key auto_increment , brandid int references brands(id) , categoryid int references categories(id) , name varchar(100) );

create table joint ( productid int references products(id) , venderid int references venders(id) , price int ,link varchar(150) , imagelink varchar(150)) ;