package sgsits.cse.dis.administration.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.CleanlinessComplaints;

//Cleanliness Complaint

@Repository("")
public interface CleanlinessComplaintRepository extends JpaRepository<CleanlinessComplaints, Long> {

}