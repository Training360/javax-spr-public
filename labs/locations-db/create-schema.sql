use mysql;

create schema if not exists locations default character set utf8 collate utf8_hungarian_ci;

create user if not exists 'locations_user'@'localhost' identified by 'locations_user';
grant all on locations.* to 'locations_user'@'localhost';

use locations;

