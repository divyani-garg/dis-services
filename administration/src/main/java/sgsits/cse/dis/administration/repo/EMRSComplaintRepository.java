package sgsits.cse.dis.administration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.EMRSComplaints;

//Electrical Maintenance and repairs section

@Repository("emrsComplaintRepository")
public interface EMRSComplaintRepository extends JpaRepository<EMRSComplaints, Long> {
	List<EMRSComplaints> findByLocationAndStatus(String location, String status);
	List<EMRSComplaints> findByCreatedBy(Long id);
	List<EMRSComplaints> findByLocation(String loc);
	List<EMRSComplaints> findByLocationAndStatusNot(String loc, String string);
	List<EMRSComplaints> findByLocationInAndStatus(List<String> location, String string);
	List<EMRSComplaints> findByLocationInAndStatusNot(List<String> location, String string);
	List<EMRSComplaints> findByLocationIn(List<String> location);
	long countByLocationAndStatusNot(String loc, String string);
	long countByLocationAndStatus(String loc, String string);
	long countByLocation(String loc);
	boolean existsByLocationAndDetailsAndStatusNot(String location, String details, String status);
	
	@Query(value = "select max(form_id) from electrical_maintenance_and_repairs_section_complaints", nativeQuery = true)
	long maxOfFormId();
}