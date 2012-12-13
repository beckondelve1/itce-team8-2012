package postech.itce.team8.model.mapper;

import java.util.List;

import postech.itce.team8.model.domain.Consultation;
import postech.itce.team8.model.domain.Patient;

public interface ConsultationMapper {

	List<Consultation> findConsultationsByDoctorName(String doctorName);
}
