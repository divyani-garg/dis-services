package sgsits.cse.dis.administration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.StudentComplaints;

//Student Complaint

@Repository("")
public interface StudentComplaintRepository extends JpaRepository<StudentComplaints, Long> {
	List<StudentComplaints> findByCreatedBy(Long id);
	List<StudentComplaints> findByStatus(String status);
	List<StudentComplaints>findByStatusNot(String status);
	long countByStatusNot(String status);
	long countByCreatedBy(long id);
	long countByStatus(String status);
	boolean existsByCreatedByAndStudentRollNoAndStudentNameAndYearAndStatusNot(long id, String studentRollNo,
			String studentName, String year, String status);
	boolean existsByCreatedByAndStudentRollNoAndYearAndStatusNot(long id, String studentRollNo, String year,
			String status);
	boolean existsByCreatedByAndStudentNameAndYearAndStatusNot(long id, String studentName, String year, String status);
}