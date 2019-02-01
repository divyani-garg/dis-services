package sgsits.cse.dis.administration.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.TelephoneComplaints;

//Cleanliness Complaint

@Repository("")
public interface TelephoneComplaintRepository extends JpaRepository<TelephoneComplaints, Long> {

}