package sgsits.cse.dis.administration.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.FacultyComplaints;

//Faculty Complaint

@Repository("")
public interface FacultyComplaintRepository extends JpaRepository<FacultyComplaints, Long> {

}