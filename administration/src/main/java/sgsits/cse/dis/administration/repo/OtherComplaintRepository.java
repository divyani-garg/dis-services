package sgsits.cse.dis.administration.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.OtherComplaints;

//Other Complaints

@Repository("")
public interface OtherComplaintRepository extends JpaRepository<OtherComplaints, Long>{

}