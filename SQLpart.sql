drop database  insurance_company;
create database insurance_company;
use insurance_company;
show databases;

create table person(
	id int primary key auto_increment,
	first_name varchar(25) not null,
	last_name varchar(25)not null,
	birthday Date not null,
	address  varchar(35) null,
	diseases varchar(40) null,
	phone_number varchar(40) not null
); 



create table categories(
	id int primary key auto_increment not null,
	subscrube_period int not null,
	coverage_ratio int not null,
	catrgouriy_cost int not null
);


create table active_member(
	id int primary key auto_increment not null,
    from_date date not null,
	to_date date not null,
	categorie_id int,
	persoin_id int,
	foreign key(categorie_id) references categories(id),
	foreign key(persoin_id) references person(id)
);


create table type_servies(
	id int primary key auto_increment not null,
	type_servies_cost double not null,
	ts_name varchar(30) not null
);




create table medical_entity_type(
id int primary key auto_increment not null,
type_name varchar(30) not null
);


create table medical_entity( 
	id int primary key auto_increment not null,
	address varchar(45) not null,
	m_name varchar(45) not null,
	phone_number varchar(40) null,
	medical_entity_type_id int,
    foreign key(medical_entity_type_id) references medical_entity_type(id)
);




create table work_days( 
	id int primary key auto_increment not null,
    day_name varchar(15) not null,
	medical_entity_id int not null,
	foreign key(medical_entity_id) references medical_entity(id)
);




create table visit(
	id int primary key auto_increment not null,
	company_payment double not null,
	date_visit date not null,
	member_payment int not null,
	medical_entity_id int,
	member_id int,
    foreign key(medical_entity_id) references medical_entity(id),
	foreign key(member_id) references active_member(id)
);

 

create table services_provided (
	id int primary key auto_increment not null,
    medical_entity_id int not null,
	type_servies_id int not null,
	foreign key(type_servies_id) references type_servies(id),
	foreign key(medical_entity_id) references medical_entity(id)
);


create table received_servies(
	id int primary key auto_increment not null,
    visit_id int,
	type_servies_id int,
	detels varchar(60),
	foreign key(type_servies_id) references type_servies(id),
	foreign key(visit_id) references visit(id)
);




insert into person(id,first_name,last_name,birthday,address,diseases,phone_number) values
(1,"qossay","zeinelddin","2000-10-18","nablus","nothing","0569"),
(2,"ameena","jadallah","1999-02-16","rammallh","nothing","056"),
(3,"hashem","jadallah","2004-05-06","rammallh","nothing","056"),
(4,"yousef","jadallah","2007-02-13","rammallh","nothing","056"),
(5,"mohammad","jadallah","1972-07-12","rammallh","nothing","056"),
(6,"amr","shayeb","1999-08-22","nablus","nothing","056"),
(7,"eman","almohsan","1978-04-18","amman","nothing","056"),
(8,"jawad","zeinelddin","1974-08-02","nablus","nothing","056"),
(9,"eman","jadallah","2006-08-27","rammallh","nothing","056"),
(10,"yaser","jadallah","2009-12-19","rammallh","nothing","056");

insert into person(id,first_name,last_name,birthday,address,diseases,phone_number) values
(11,"yaser","jadallah","2009-1-19","rammallh","nothing","056");



insert into categories (id,subscrube_period,coverage_ratio,catrgouriy_cost) values
(1,12,100,2000 ),
(2,12,75,1600 ),
(3,12,50, 1200),
(4,6,100, 1000 ),
(5,6,75,800 ),
(6,6,50,600 );


insert into active_member(id,from_date,to_date,categorie_id,persoin_id) value
(1,"2021-1-18","2022-1-18",1,1),
(2,"2021-2-16","2022-2-16",2,2),
(3,"2021-5-18","2022-5-18",3,3),
(4,"2021-5-18","2021-11-18",4,4),
(5,"2021-3-20","2021-6-20",5,5),
(6,"2021-4-22","2021-10-22",6,6);



insert into type_servies (id,ts_name,type_servies_cost) values
 (1,"عملية ازالة لوز", 2000),
 (2,'عملية ولادة ', 5000),
 (3,"عملية الزايدة", 2500),
 (4,"عملية ازالة عملية زراعة شعر", 8000),
 (5,"عملية ازالة عملية تصحيح نظر", 6000),
 (6,"فحص بول", 40),
 (7,"فحص دم", 20),
 (8,"فحص B12", 170),
 (9,"فحص تلاسيميا ", 220),
 (10," فحص سكري ", 10),
 (11,"فحص فيتاميين دال ", 70),
 (12," دواء خافض حرارة للاطفال   ", 10),
 (13," دواء اكمول", 10 ),
 (14," دواء تحاميل ", 7 ),
 (15,"دواء للضغط ", 25),
 (16," دواء مضاد حيوي للوزتين  ", 60),
 (17,"دواء مكمل V.B12 " , 40),
 (18,"  دواء كرتزول ", 50),
 (19,"دواء مكمل V.D   ", 90),
 (20," كشفيت فحص اطفال  ", 40),
 (21,"كشفية نساء حوامل    ", 100),
 (22,"  كشفيت فحص الجنين ", 100),
 (23,"كشفيت بالغين   ", 2000);
 




insert into medical_entity_type values
(1 , "hospital"),
(2 , "pharmacy"),
(3 , "doctor"),
(4 , "medical examinations center"),
(5 , "hair transplant center");



insert into medical_entity(id,address,m_name,phone_number,medical_entity_type_id) value
 (1,"nablus","An-Najah National University Hospital","+970 9 233 1471",1),
 (2,"ramallah" , "Istishari Hospital" , "+970 2 294 3200" , 1),
 (3,"ramallah" , "Palestine Medical Complex","+970 2 298 2222",1),
 (4,"nablus" , "Jawad Pharmacy" , "92372139",2),
 (5,"ramallah" , "Oxygen Pharmacy ","02 295 6256",2),
 (6,"Beit Ur al-Tahta - ramallah","Yellow pages","02-2488727",2),
 (7,"ramallah","Nizar L.khalil", "02989275",3),
 (8 ,"ramallah","Marridi Hand Surgery", "0597135234",3),
 (9,"nablus","Mazen Muhammad Kazlik","0598480030",3),
 (10,"ramallah"," Medicare Labs","+972 2-294-5444",4),
 (11, "ramallah","Turkish Hair","0595 131 415",5);
 


insert into work_days ( id, day_name , medical_entity_id  ) value 
(1, "Saturday",1),(2 ,"Sunday",1),(3 ,"Monday",1),(4, "Tuesday",1), (5, "Wednesday",1),(6 ,"Thursday",1),(7 ,"Friday",1),
(8, "Saturday",2),(9 ,"Sunday",2),(10 ,"Monday",2),(11, "Tuesday",2), (12, "Wednesday",2),(13 ,"Thursday",2),(14 ,"Friday",2),
(15, "Saturday",3),(16 ,"Sunday",3),(17 ,"Monday",3),(18, "Tuesday",3), (19, "Wednesday",3),(20 ,"Thursday",3),(21 ,"Friday",3),
(22, "Saturday",4),(23 ,"Sunday",4),(24 ,"Monday",5),(25, "Tuesday",5), (26, "Wednesday",6),(27 ,"Thursday",6),(28 ,"Friday",6),
(29, "Saturday",7),(30 ,"Sunday",7),(31 ,"Monday",8),(32, "Tuesday",8), (33, "Wednesday",9),(34 ,"Thursday",9),(35 ,"Friday",10),
(36, "Saturday",11);

insert into visit (id,company_payment,date_visit,member_payment,medical_entity_id,member_id) values
(1, 2500 , '2021-2-16' , 0 , 1,1),
(2, 4500 , '2021-11-26' , 1500 , 3,2),
(3, 1000 , '2021-2-16' , 1000 , 7,3),
(4, 40 , '2021-7-30' , 0 , 6,4),
(5, 52.5 , '2021-3-5' , 17.5 , 10,5),
(6, 4000 , '2021-8-22' , 4000 , 11,6);
insert into visit (id,company_payment,date_visit,member_payment,medical_entity_id,member_id) values
(7,300 , '2022-8-22' , 100 , 3,2);


insert into services_provided (id, medical_entity_id, type_servies_id) values
(1, 1 ,3 ),
(2, 3,5 ),
(3, 6 , 17),
(4 ,7,23 ),
(5, 10 ,11),
(6 ,11 , 4);
 


insert into received_servies( id, visit_id, type_servies_id ,detels) values

(1 , 1, 3 , " don't act and stay in bed for two weeks "),
(2 , 2, 5, "Do not expose to the sun "),
(3 , 3, 23 , "Do not expose to the sun "),
(4 , 4, 17 , "3 times a day after eating "),
(5 , 5, 11 , "nothing"),
(6 , 6, 4 , "nothing ");





select a.id , a.from_date , a.to_date , a.categorie_id , a.persoin_id from person p , active_member a 
where p.id = a.persoin_id and
p.first_name like '%qos%' or p.last_name like '%qo%';


select * from active_member a
where a.to_date like '2021-11-%';

select * from active_member a
where a.to_date like '2021-11-%';


select sum(v.company_payment)  , sum(v.member_payment)  from person p , visit v , active_member a , categories c 
 where v.member_id = a.id and
 a.persoin_id = p.id and
 a.categorie_id = c.id and
 v.date_visit like '2021-03-%';


select distinct * from person p 
where p.id  in (select   a.persoin_id from active_member a);

select distinct * from person p 
where p.id not in (select   a.persoin_id from active_member a);


select * from person p
where
address like '%nablus%' ;

select count(*) from person;



select max(v.date_visit) from person p , visit v , active_member a
 where v.member_id = a.id and
 a.persoin_id = p.id ;
 
 select min(v.date_visit) from person p , visit v , active_member a
 where v.member_id = a.id and
 a.persoin_id = p.id ;
 
 select sum(v.company_payment)  , sum(v.member_payment) , sum(c.catrgouriy_cost) from person p , visit v , active_member a , categories c 
 where v.member_id = a.id and
 a.persoin_id = p.id and
 a.categorie_id = c.id and
 v.date_visit like '2021%';
 
 
select m.id , m.address ,m.m_name ,m.phone_number , m.medical_entity_type_id from medical_entity m ,  medical_entity_type t
where
m.medical_entity_type_id = t.id and
t.type_name like '%hos%';


select * from medical_entity m
where
m.address like '%nablus%';
 
 select distinct v.id,v.company_payment,v.date_visit,v.member_payment,v.medical_entity_id,v.member_id from visit v , active_member a  , person p
 where p.id = a.persoin_id and
 a.id = v.member_id and
 p.first_name like '%qoss%' or p.last_name like '%jad%';
 
 select  distinct w.id, w.day_name , w.medical_entity_id from work_days w , medical_entity m
 where  w.medical_entity_id = m.id and
 m.m_name like'%Mazen%';
 
 select * from type_servies t
where t.type_servies_cost >= 4000;

 
 
 
