use cinema;

select * from flight;
select * from passenger;
select * from booking;
select * from airplane;
select * from schedule;
select * from seat;
select * from destination;
select * from reserved_seat;

SET FOREIGN_KEY_CHECKS=0;
drop table flight;
drop table passenger;
drop table booking;
drop table airplane;
drop table schedule;
drop table seat;
drop table destination;
drop table reserved_seat;
SET FOREIGN_KEY_CHECKS=1;