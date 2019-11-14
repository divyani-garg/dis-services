package sgsits.cse.dis.administration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.OtherComplaints;

//Other Complaints

@Repository("")
public interface OtherComplaintRepository extends JpaRepository<OtherComplaints, Long>{
	List<OtherComplaints> findByCreatedBy(Long id);
	List<OtherComplaints> findByAssignedToAndStatus(long id, String status);
	List<OtherComplaints> findByStatus(String status);
	List<OtherComplaints> findByAssignedTo(Long id);
	List<OtherComplaints> findByStatusNot(String string);
	List<OtherComplaints> findByAssignedToAndStatusNot(Long id, String string);
	long countByStatusNot(String string);
	long countByAssignedToAndStatusNot(long id, String string);
	long countByCreatedBy(long id);
	long countByStatus(String string);
	long countByAssignedToAndStatus(long id, String string);
	long countByAssignedTo(long id);
	boolean existsByCreatedByAndDetailsAndStatusNot(long id, String details, String string);
}