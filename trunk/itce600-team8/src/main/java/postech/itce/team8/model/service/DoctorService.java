package postech.itce.team8.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import postech.itce.team8.model.domain.Doctor;
import postech.itce.team8.model.mapper.DoctorMapper;

@Service
public class DoctorService {

	@Autowired
	private DoctorMapper doctorMapper;
	
	//
	public List<Doctor> findDoctorList(){
		return doctorMapper.findDoctorList();
	}
	
	public List<String> findDoctorUserNameList(){
		return doctorMapper.findDoctorUserNameList();
	}
	
	@Transactional(rollbackFor = java.lang.Exception.class)
	public void insertDoctor(Doctor doctor){
		doctorMapper.insertDoctor(doctor);
	}
	
	public boolean isDoctorExisted(String userName){
		return doctorMapper.isDoctorExisted(userName) > 0;
	}
}
