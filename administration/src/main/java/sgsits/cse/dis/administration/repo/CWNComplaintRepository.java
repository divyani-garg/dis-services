package sgsits.cse.dis.administration.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.CWNComplaints;

//CWN Maintenance

@Repository("")
public interface CWNComplaintRepository extends JpaRepository<CWNComplaints, Long> {

}