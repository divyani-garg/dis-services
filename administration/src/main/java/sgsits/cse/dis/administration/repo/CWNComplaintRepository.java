package sgsits.cse.dis.administration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.CWNComplaints;

//CWN Maintenance

@Repository("cwnComplaintRepository")
public interface CWNComplaintRepository extends JpaRepository<CWNComplaints, Long> {
	List<CWNComplaints> findByCreatedBy(Long id);
	List<CWNComplaints> findByLocationAndStatus(String location, String status);
	List<CWNComplaints> findByLocation(String loc);
	List<CWNComplaints> findByLocationAndStatusNot(String loc, String status);
	List<CWNComplaints> findByLocationInAndStatus(List<String> location, String status);
	List<CWNComplaints> findByLocationInAndStatusNot(List<String> location, String status);
	List<CWNComplaints> findByLocationIn(List<String> location);
	long countByLocationAndStatusNot(String loc, String status);
	long countByLocationAndStatus(String loc, String status);
	long countByLocation(String loc);
	boolean existsByLocationAndDetailsAndStatusNot(String location, String details, String status);
	
	@Query(value = "select max(form_id) from cwn_maintenance_complaints", nativeQuery = true)
	long maxOfFormId();
}