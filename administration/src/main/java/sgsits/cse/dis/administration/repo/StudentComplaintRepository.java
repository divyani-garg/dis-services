package sgsits.cse.dis.administration.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.StudentComplaints;

//Student Complaint

@Repository("")
public interface StudentComplaintRepository extends JpaRepository<StudentComplaints, Long> {

}