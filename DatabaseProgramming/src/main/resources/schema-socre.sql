show databases;

create database if not exists `database_programming`;
use `database_programming`;

drop table if exists `score`;
create table `score` (
	`name` varchar(20) primary key,
	`kor` int,
	`eng` int,
	`math` int,
	`total` int,
	`average` int
)engine=InnoDB default charset=utf8;

insert into score(`name`, `kor`, `eng`, `math`, `total`, `average`) VALUES('연아', 100, 90, 90, 280, 93);
insert into score(`name`, `kor`, `eng`, `math`, `total`, `average`) VALUES('현진', 80, 80, 80, 240, 80);
insert into score(`name`, `kor`, `eng`, `math`, `total`, `average`) VALUES('장훈', 100, 65, 50, 215, 71);
insert into score(`name`, `kor`, `eng`, `math`, `total`, `average`) VALUES('지성', 70, 70, 70, 210, 70);
insert into score(`name`, `kor`, `eng`, `math`, `total`, `average`) VALUES('연경', 50, 80, 55, 185, 61);
insert into score(`name`, `kor`, `eng`, `math`, `total`, `average`) VALUES('흥민', 50, 50, 55, 155, 51);

show tables;

delimiter $$
create procedure selectScoreProcedure()
begin
	select name, kor, eng, math, total, average, rank() over(order by total desc) as ran, (case when average>= 90 then 'A' when average>=80 then 'B' when average>=70 then 'C' when average>=60 then 'D' else 'F' end) as grade from score;
end $$
delimiter ;
drop procedure selectScoreProcedure;

delimiter $$
create procedure deleteScoreProcedure(
	IN s_name varchar(20) character set utf8)
begin
	delete from score where name=s_name;
end $$
delimiter ;
drop procedure deleteScoreProcedure;

delimiter $$
create procedure insertScoreProcedure(
	IN s_name varchar(20) character set utf8, s_kor int, s_eng int, s_math int, s_total int, s_average int)
begin
	insert into score values(s_name, s_kor, s_eng, s_math, s_total, s_average);
end $$
delimiter ;
drop procedure insertScoreProcedure;

delimiter $$
create procedure updateScoreProcedure(
	IN s_name varchar(20) character set utf8, s_kor int, s_eng int, s_math int, s_total int, s_average int)
begin
	update score set kor=s_kor, eng=s_eng, math=s_math, total=s_total, average=s_average where name=s_name;
end $$
delimiter ;
drop procedure updateScoreProcedure;

show procedure status;