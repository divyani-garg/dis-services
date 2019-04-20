package sgsits.cse.dis.administration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.TelephoneComplaints;

//Cleanliness Complaint

@Repository("")
public interface TelephoneComplaintRepository extends JpaRepository<TelephoneComplaints, Long> {
	List<TelephoneComplaints> findByLocationAndStatus(String location, String status);
	List<TelephoneComplaints> findByCreatedBy(Long id);
	List<TelephoneComplaints> findByLocation(String loc);
	List<TelephoneComplaints> findByLocationAndStatusNot(String loc, String status);
	List<TelephoneComplaints> findByLocationInAndStatus(List<String> location, String status);
	List<TelephoneComplaints> findByLocationInAndStatusNot(List<String> location, String status);
	List<TelephoneComplaints> findByLocationIn(List<String> location);
	long countByLocationAndStatusNot(String loc, String status);
	long countByLocationAndStatus(String loc, String status);
	long countByLocation(String loc);
	boolean existsByExtensionNoAndLocationAndDetailsAndStatusNot(int extensionNo, String location, String details,
			String status);
	@Query(value = "select max(form_id) from telephone_complaints", nativeQuery = true)
	long maxOfFormId();
}