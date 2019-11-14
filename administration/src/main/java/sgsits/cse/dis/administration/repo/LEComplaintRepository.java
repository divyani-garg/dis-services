package sgsits.cse.dis.administration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.LEComplaints;

//Lab Equipment Complaint

@Repository("")
public interface LEComplaintRepository extends JpaRepository<LEComplaints, Long> {
	List<LEComplaints> findByCreatedBy(Long id);
	List<LEComplaints> findByLabAndStatus(String lab, String status);
	List<LEComplaints> findByLab(String loc);
	List<LEComplaints> findByLabAndStatusNot(String lab, String string);
	List<LEComplaints> findByLabInAndStatus(List<String> location, String string);
	List<LEComplaints> findByLabInAndStatusNot(List<String> location, String string);
	List<LEComplaints> findByLabIn(List<String> location);
	long countByLabAndStatusNot(String loc, String string);
	long countByCreatedBy(long id);
	long countByLabAndStatus(String loc, String string);
	long countByLab(String loc);
	boolean existsByCreatedByAndLabAndSystemNoAndStatusNot(long id, String lab, String systemNo, String status);
}