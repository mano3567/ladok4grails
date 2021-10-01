drop database if exists ladok;
create database ladok charset utf8 collate utf8_swedish_ci;
drop user if exists 'ladok'@'localhost';
create user 'ladok'@'localhost' identified by 'ladok';
grant all on ladok.* to 'ladok'@'localhost';
