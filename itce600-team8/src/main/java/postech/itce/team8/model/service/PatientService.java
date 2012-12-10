package postech.itce.team8.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import postech.itce.team8.model.domain.Doctor;
import postech.itce.team8.model.domain.Patient;
import postech.itce.team8.model.mapper.DoctorMapper;
import postech.itce.team8.model.mapper.PatientMapper;

@Service
public class PatientService {

	@Autowired
	private PatientMapper patientMapper;
	
	//
	public List<Patient> findPatientsByDoctorName(String doctorName){
		return patientMapper.findPatientsByDoctorName(doctorName);
	}
}
