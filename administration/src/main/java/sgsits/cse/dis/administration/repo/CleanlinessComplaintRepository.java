package sgsits.cse.dis.administration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.CleanlinessComplaints;

//Cleanliness Complaint

@Repository("")
public interface CleanlinessComplaintRepository extends JpaRepository<CleanlinessComplaints, Long> {
	List<CleanlinessComplaints> findByCreatedBy(String username);
	List<CleanlinessComplaints> findByLocationAndStatus(String location, String status);
}