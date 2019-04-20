package sgsits.cse.dis.administration.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.FacultyResourceRequest;

@Repository("facultyResourceRequestRepository")
public interface FacultyResourceRequestRepository extends JpaRepository<FacultyResourceRequest, Long>{

	boolean existsByCreatedByAndResourceAndDetailsAndStatusNot(long id, String resource, String details, String status);
	
}
