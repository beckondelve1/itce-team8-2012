package postech.itce.team8.model.mapper;

import java.util.List;

import postech.itce.team8.model.domain.Patient;

public interface PatientMapper {

	List<Patient> findPatientsByDoctorName(String doctorName);
}
