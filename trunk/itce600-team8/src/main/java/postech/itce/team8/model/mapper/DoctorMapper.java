package postech.itce.team8.model.mapper;

import java.util.List;

import postech.itce.team8.model.domain.Doctor;

public interface DoctorMapper {

  List<Doctor> findDoctorList();
  
  List<String> findDoctorUserNameList();
  
  void insertDoctor(Doctor doctor);
  
  int isDoctorExisted(String userName);
	  
}