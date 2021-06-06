show databases;

create database if not exists `database_programming`;
use `database_programming`;

drop table if exists `tb_staff`;
create table `tb_staff` (
  `id` varchar(10) NOT NULL,
  `pwd` varchar(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  primary key (id)
) engine=InnoDB default charset=utf8;

describe tb_staff;

insert into tb_staff(`id`, `pwd`, `name`) values('jin', '1234', '문학진');

drop table if exists `tb_staff_info`;
create table `tb_staff_info` (
  `id` varchar(10) NOT NULL,
  `tel` varchar(20) NOT NULL,
  `address` varchar(20) NOT NULL,
  `birth` varchar(20) NOT NULL,
  `job` varchar(20) NOT NULL,
  `gender` varchar(20) NOT NULL,
  `email` varchar(20) NOT NULL,
  `intro` varchar(20) NOT NULL,
  foreign key (id) references tb_staff (id)
) engine=InnoDB default charset=utf8;

describe tb_staff_info;

insert into tb_staff_info(`id`, `tel`, `address`, `birth`, `job`, `gender`, `email`, `intro`) VALUES('jin', '010-3099-4529', '수원시', '19951130', '직장인', 'M', 'hjm7091@naver.com', '안녕하세요.');

show tables;