create table `student` (
    `student_id` int(11) not null auto_increment,
    `first_name` varchar(50),
    `last_name` varchar(50),
    primary key (`student_id`)
);

select * from student