use mysql;

create schema if not exists employees default character set utf8 collate utf8_hungarian_ci;

create user 'employees'@'localhost' identified by 'employees';
grant all on employees.* to 'employees'@'localhost';

use employees;

create table employees (id bigint auto_increment,
  emp_name varchar(255),
    constraint pk_employee primary key (id));

