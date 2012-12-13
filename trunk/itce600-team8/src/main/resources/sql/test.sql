
#SELECT ADDTIME('2007-12-31 23:59:59.999999', '1') from dual

#select * from consultation
#insert into consultation values(null,1,1,null,null,null,now(),'NEW')
#insert into consultation values(null,1,1,null,null,null,addtime(now(),'1'),'NEW')
#insert into consultation values(null,1,2,null,null,null,now(),'NEW')

SELECT d.id as doctorId, p.id as patientId, p.fullname as patientFullName, c.start_time as startTime, end_time as endTime, expected_time as expectedTime, status as status
FROM  patient as p, doctor as d, consultation as c
		WHERE d.id = c.doctor_id and p.id = c.patient_id and d.username='mehaoua'


#select * from doctor

/*
update doctor 
set fullname='Ahmed Mehaoua', 
username='mehaoua',
password=md5('mehaoua')
where id = 1
*/