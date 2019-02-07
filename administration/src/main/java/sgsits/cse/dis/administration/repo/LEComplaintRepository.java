package sgsits.cse.dis.administration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.LEComplaints;

//Lab Equipment Complaint

@Repository("")
public interface LEComplaintRepository extends JpaRepository<LEComplaints, Long> {
	List<LEComplaints> findByCreatedBy(String username);
	List<LEComplaints> findByLabAndStatus(String lab, String status);

}