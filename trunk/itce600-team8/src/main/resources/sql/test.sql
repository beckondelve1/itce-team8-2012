
select * from patient

--insert into patient values(null,'Huu Hiep Nguyen','hiepnguyen',md5('hiepnguyen'),'POSTECH','119.202.84.41','rtsp://119.202.84.41:554/onvif/profile4/media.smp','admin','4321');
--insert into patient values(null,'Dongwoo Kwon','dwkwon',md5('dwkwon'),'POSTECH','119.202.84.42','rtsp://119.202.84.42:554/onvif/profile4/media.smp','admin','4321');

/*
update patient 
set fullname='Huu Hiep Nguyen', username='hiepnguyen',password=md5('hiepnguyen'), ip_address='119.202.84.41',
default_profile_url='rtsp://119.202.84.41:554/onvif/profile4/media.smp', camera_user='admin', camera_pass='4321'
where id = 1
*/

--SELECT ADDTIME('2007-12-31 23:59:59.999999', '1') from dual
--
--insert into doctor_patient values(1,1,now());
--insert into doctor_patient values(1,2,now());
--
select * from consultation;
insert into consultation values(null,1,1,null,null,now(),'NEW');
insert into consultation values(null,1,1,null,null,addtime(now(),'1'),'NEW');
insert into consultation values(null,1,2,null,null,now(),'NEW');

/*
SELECT d.id as doctorId, p.id as patientId, p.fullname as patientFullName, c.start_time as startTime, end_time as endTime, expected_time as expectedTime, status as status
FROM  patient as p, doctor as d, consultation as c
		WHERE d.id = c.doctor_id and p.id = c.patient_id and d.username='mehaoua'
*/

select * from doctor

--insert into doctor values(null, 'Hiep.Nguyen', 'hiepnh', md5('hiepnh'));

/*
update doctor 
set fullname='Hiep.Nguyen', 
username='hiepnh',
password=md5('hiepnh')
where id = 1
*/


