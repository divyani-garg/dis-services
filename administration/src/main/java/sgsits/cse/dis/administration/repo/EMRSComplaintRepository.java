package sgsits.cse.dis.administration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.EMRSComplaints;

//Electrical Maintenance and repairs section

@Repository("")
public interface EMRSComplaintRepository extends JpaRepository<EMRSComplaints, Long> {
	List<EMRSComplaints> findByLocationAndStatus(String location, String status);
}