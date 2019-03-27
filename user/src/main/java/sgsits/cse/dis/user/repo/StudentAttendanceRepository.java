package sgsits.cse.dis.user.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.user.model.StudentAttendance;

@Repository("studentAttendanceRepository")
public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance, Long>{
	List<StudentAttendance> findByEnrollmentIdAndSubjectCode(String enrollmentId, String subjectCode);
	List<StudentAttendance> findByEnrollmentIdAndSubjectCodeAndClassTypeAndAttendanceNot(String enrollment,
			String subjectCode, char c, int i);
	List<StudentAttendance> findByEnrollmentIdAndSubjectCodeAndClassType(String enrollment, String subjectCode, char c);
	
	
}
